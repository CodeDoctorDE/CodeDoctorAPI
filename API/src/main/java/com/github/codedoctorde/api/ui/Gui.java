package com.github.codedoctorde.api.ui;

import com.github.codedoctorde.api.ui.template.item.InputItem;
import com.github.codedoctorde.api.ui.template.item.ValueItem;
import com.github.codedoctorde.api.ui.template.item.events.InputItemEvent;
import com.github.codedoctorde.api.ui.template.item.events.ValueItemEvent;
import com.github.codedoctorde.api.utils.ItemStackBuilder;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Gui implements Listener {
    static HashMap<Player, Gui> playerGuiHashMap = new HashMap<>();
    private final GuiEvent guiEvent;
    private final JavaPlugin plugin;
    private final HashMap<Player, Integer> taskID = new HashMap<>();
    private String title;
    private int size;
    private boolean registered = false;
    private final HashMap<Integer, GuiItem> guiItems = new HashMap<>();
    private Inventory inventory = null;


    public Gui(JavaPlugin javaPlugin) {
        guiEvent = new GuiEvent() {
        };
        plugin = javaPlugin;
        title = "";
        size = 3;
    }

    public Gui(JavaPlugin javaPlugin, String title, int size) {
        guiEvent = new GuiEvent() {
        };
        plugin = javaPlugin;
        this.title = title;
        this.size = size;
    }

    public Gui(JavaPlugin javaPlugin, int size) {
        plugin = javaPlugin;
        this.title = "";
        this.size = size;
        guiEvent = new GuiEvent() {
        };
    }

    public Gui(JavaPlugin javaPlugin, GuiEvent guiEvent) {
        this.guiEvent = guiEvent;
        size = 3;
        this.title = "";
        plugin = javaPlugin;
    }

    public Gui(JavaPlugin javaPlugin, String title, int size, GuiEvent guiEvent) {
        this.guiEvent = guiEvent;
        plugin = javaPlugin;
        this.title = title;
        this.size = size;
    }

    public Gui(JavaPlugin javaPlugin, int size, GuiEvent guiEvent) {
        this.guiEvent = guiEvent;
        this.title = "";
        plugin = javaPlugin;
        this.size = size;
    }

    public Gui(JavaPlugin javaPlugin, String title, GuiEvent guiEvent) {
        this.guiEvent = guiEvent;
        this.title = title;
        plugin = javaPlugin;
        this.size = 3;
    }

    public static void reload(final Player... players) {
        for (Player player : players) {
            if (!playerGuiHashMap.containsKey(player))
                return;
            player.getOpenInventory().getTopInventory().clear();
            playerGuiHashMap.get(player).getGuiItems().forEach((integer, guiItem) -> reload(player, integer));
        }
    }

    public static void reload(final Player player, int integer){
        if (!playerGuiHashMap.containsKey(player))
            return;
        player.getOpenInventory().getTopInventory().setItem(integer, playerGuiHashMap.get(player).guiItems.get(integer).getItemStack());
    }

    public void reload(){
        playerGuiHashMap.entrySet().stream().filter(playerGuiEntry -> playerGuiEntry.getValue().equals(this)).forEach(playerGuiEntry -> reload(playerGuiEntry.getKey()));
    }
    public void reload(int integer){
        playerGuiHashMap.entrySet().stream().filter(playerGuiEntry -> playerGuiEntry.getValue().equals(this)).forEach(playerGuiEntry -> reload(playerGuiEntry.getKey(), integer));
    }


    public static Gui getGui(Player player) {
        return playerGuiHashMap.get(player);
    }

    public static boolean hasGui(Player player) {
        return playerGuiHashMap.containsKey(player);
    }

    public void open(Player... players) {
        Arrays.stream(players).forEach(player -> {
            player.closeInventory();
            playerGuiHashMap.put(player, this);
            player.openInventory(build());
            startTick(player);
            raiseInventoryOpenEvent(player);
        });
        register();
    }
    public void register(){
        if(getCurrentPlayers().length <= 0 || registered)
            return;
        Bukkit.getPluginManager().registerEvents(this, plugin);
        registered = true;
    }
    public void unregister(){
        if(getCurrentPlayers().length > 0 || !registered)
            return;
        HandlerList.unregisterAll(this);
        registered = false;
    }
    public void close(Player... players){
        close(true, players);
        unregister();
    }
    public void close(boolean raiseEvent, Player... players) {
        if(players.length <= 0)
            players = getPlayers();
        Arrays.stream(players).filter(player -> playerGuiHashMap.containsKey(player)).forEach(player -> {
            if (raiseEvent)
                playerGuiHashMap.get(player).raiseInventoryCloseEvent(player);
            stopTick(player);
            playerGuiHashMap.remove(player);
            player.closeInventory();
        });
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player))
            return;
        Player player = (Player) event.getPlayer();
        if (playerGuiHashMap.containsKey(player) && playerGuiHashMap.get(player) == this)
            playerGuiHashMap.get(player).close(player);
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (!(event.getWhoClicked() instanceof Player))
            return;
        Player player = (Player) event.getWhoClicked();
        if (playerGuiHashMap.containsKey(player) && playerGuiHashMap.get(player) == this && event.getInventory().equals(event.getView().getTopInventory()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player))
            return;
        Player player = (Player) event.getWhoClicked();
        if (playerGuiHashMap.containsKey(player)) if (playerGuiHashMap.get(player) == this) {
            Gui gui = playerGuiHashMap.get(player);
            if ((event.getClickedInventory() != player.getInventory()) ||
                    (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY && event.getClickedInventory().equals(player.getInventory()))) {
                event.setCancelled(true);
                if (guiItems.containsKey(event.getSlot())) {
                    GuiItem guiItem = guiItems.get(event.getSlot());
                    guiItem.raiseEvent(gui, event);
                }
            }
        }
    }
    public Player[] getCurrentPlayers(){
        return playerGuiHashMap.entrySet().stream().filter(entry -> entry.getValue().equals(this)).map(Map.Entry::getKey).toArray(Player[]::new);
    }

    public void startTick(Player player) {
        taskID.put(player, Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> runTick(player), 1, 1));
    }

    private void runTick(Player player) {
        guiEvent.onTick(this, player);
        guiItems.values().forEach(guiItem -> guiItem.runTick(this, player));
    }

    void stopTick(final Player player) {
        if (taskID.containsKey(player))
            Bukkit.getScheduler().cancelTask(taskID.get(player));
        taskID.remove(player);
    }

    public HashMap<Integer, GuiItem> getGuiItems() {
        return guiItems;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    void raiseInventoryCloseEvent(final Player player) {
        guiEvent.onClose(this, player);
    }

    void raiseInventoryOpenEvent(final Player player) {
        guiEvent.onOpen(this, player);
    }

    public int[] getFreeSpaces(){
        return IntStream.range(0, size * 9 - 1).filter(i -> !guiItems.containsKey(i)).boxed().mapToInt(i->i).toArray();
    }

    public void addGuiItem(final GuiItem guiItem){
        int[] freeSpaces = getFreeSpaces();
        if(freeSpaces.length > 0)
        getGuiItems().put(freeSpaces[0], guiItem);
    }
    public Inventory build() {
        inventory = build(inventory);
        return inventory;
    }

    public Inventory build(Inventory buildInventory) {
        if (buildInventory == null) buildInventory = Bukkit.createInventory(null, size * 9, title);
        buildInventory.clear();
        for (int key : guiItems.keySet()) buildInventory.setItem(key, guiItems.get(key).getItemStack());
        return buildInventory;
    }

    public Inventory buildNew() {
        inventory = Bukkit.createInventory(null, size * 9, title);
        inventory.clear();
        guiItems.keySet().forEach(key -> inventory.setItem(key, guiItems.get(key).getItemStack()));
        return inventory;
    }
    public static Player[] getPlayers(){
        return playerGuiHashMap.keySet().toArray(new Player[0]);
    }
    public void changeGui(Gui gui, Player... players){
        if(players.length <= 0)
            players = getPlayers();
        close(false, players);
        gui.open(players);
    }
}
