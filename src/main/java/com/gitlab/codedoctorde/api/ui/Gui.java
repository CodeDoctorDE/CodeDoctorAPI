package com.gitlab.codedoctorde.api.ui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class Gui implements Listener {
    static HashMap<Player, Gui> playerGuiHashMap = new HashMap<>();
    static HashMap<Player, Gui> playerInventoryHashMap = new HashMap<>();
    private final JavaPlugin plugin;
    int index = 0;
    private HashMap<Player, Integer> taskID = new HashMap<>();
    private String title;
    private int size;
    private HashMap<Integer, GuiItem> guiItems = new HashMap<>();
    private GuiEvent guiEvent;
    private Inventory inventory = null;


    public Gui(JavaPlugin javaPlugin) {
        size = 3;
        plugin = javaPlugin;
        guiEvent = new GuiEvent() {
        };
        Bukkit.getPluginManager().registerEvents(this, javaPlugin);
    }

    public Gui(JavaPlugin javaPlugin, String title, int size) {
        plugin = javaPlugin;
        this.title = title;
        this.size = size;
        guiEvent = new GuiEvent() {
        };
        Bukkit.getPluginManager().registerEvents(this, javaPlugin);
    }

    public Gui(JavaPlugin javaPlugin, int size) {
        plugin = javaPlugin;
        this.size = size;
        guiEvent = new GuiEvent() {
        };
        Bukkit.getPluginManager().registerEvents(this, javaPlugin);
    }

    public Gui(JavaPlugin javaPlugin, GuiEvent guiEvent) {
        this.guiEvent = guiEvent;
        size = 3;
        plugin = javaPlugin;
        Bukkit.getPluginManager().registerEvents(this, javaPlugin);
    }

    public Gui(JavaPlugin javaPlugin, String title, int size, GuiEvent guiEvent) {
        this.guiEvent = guiEvent;
        plugin = javaPlugin;
        this.title = title;
        this.size = size;
        Bukkit.getPluginManager().registerEvents(this, javaPlugin);
    }

    public Gui(JavaPlugin javaPlugin, int size, GuiEvent guiEvent) {
        this.guiEvent = guiEvent;
        plugin = javaPlugin;
        this.size = size;
        Bukkit.getPluginManager().registerEvents(this, javaPlugin);
    }

    public static void reload(final Player player) {
        if (!playerGuiHashMap.containsKey(player))
            return;
        player.getOpenInventory().getTopInventory().clear();
        playerGuiHashMap.get(player).getGuiItems().forEach((integer, guiItem) -> player.getOpenInventory().getTopInventory().setItem(integer, guiItem.getItemStack()));
    }

    public void reload() {
        playerGuiHashMap.forEach((player, gui) -> reload(player));
    }

    public void open(final Player... players) {
        for (Player player :
                players) {
            player.closeInventory();
            playerGuiHashMap.put(player, this);
            final Inventory inventory = build();
            player.openInventory(inventory);
            startTick(player);
            raiseInventoryOpenEvent(player);
        }
    }

    public void close(Player... players) {
        for (Player player :
                players) {
            if (playerGuiHashMap.containsKey(player)) {
                playerGuiHashMap.get(player).raiseInventoryCloseEvent(player);
                stopTick(player);
                playerGuiHashMap.remove(player);
            }
            player.closeInventory();
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player))
            return;
        Player player = (Player) event.getPlayer();
        if (playerGuiHashMap.containsKey(player))
            if (playerGuiHashMap.get(player) == this)
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
        for (int key : guiItems.keySet()) inventory.setItem(key, guiItems.get(key).getItemStack());
        return inventory;
    }

}
