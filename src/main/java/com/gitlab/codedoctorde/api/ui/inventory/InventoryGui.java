package com.gitlab.codedoctorde.api.ui.inventory;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

/**
 * @author CodeDoctorDE
 */
public class InventoryGui implements Listener {
    static HashMap<Player, InventoryGui> playerGuiHashMap = new HashMap<>();
    static HashMap<Player, ItemStack[]> playerItemsHashMap = new HashMap<>();
    protected final JavaPlugin plugin;
    private final InventoryGuiEvent guiEvent;
    private HashMap<Integer, InventoryGuiItem> guiItems = new HashMap<>();
    private HashMap<Player, Integer> taskID = new HashMap<>();

    public InventoryGui(JavaPlugin javaPlugin) {
        guiEvent = new InventoryGuiEvent() {
        };
        plugin = javaPlugin;
        Bukkit.getPluginManager().registerEvents(this, javaPlugin);
    }

    public InventoryGui(JavaPlugin javaPlugin, InventoryGuiEvent guiEvent) {
        this.guiEvent = guiEvent;
        plugin = javaPlugin;
        Bukkit.getPluginManager().registerEvents(this, javaPlugin);
    }

    public static void reload(final Player player) {
        if (!playerGuiHashMap.containsKey(player))
            return;
        player.getOpenInventory().getTopInventory().clear();
        playerGuiHashMap.get(player).getGuiItems().forEach((integer, guiItem) -> player.getOpenInventory().getTopInventory().setItem(integer, guiItem.getItemStack()));
    }

    public void open(Player... players) {
        for (Player player :
                players) {
            if (playerGuiHashMap.containsKey(player))
                close(player);
            playerItemsHashMap.put(player, player.getInventory().getContents());
            playerGuiHashMap.put(player, this);
            player.getInventory().clear();
            final ItemStack[] contents = build(player.getInventory().getContents());
            player.getInventory().setContents(contents);
            startTick(player);
            raiseInventoryOpenEvent(player);
        }
    }

    private ItemStack[] build(ItemStack[] contents) {
        guiItems.forEach((key, value) -> contents[key] = value.getItemStack());
        return contents;
    }

    public void close(Player... players) {
        for (Player player :
                players) {
            if (playerGuiHashMap.containsKey(player)) {
                playerGuiHashMap.get(player).raiseInventoryCloseEvent(player);
                stopTick(player);
                playerGuiHashMap.remove(player);
            }
            player.getInventory().clear();
            if (playerGuiHashMap.containsKey(player))
                player.getInventory().setContents(playerItemsHashMap.get(player));
        }
    }

    public void reload() {
        playerGuiHashMap.forEach((player, gui) -> reload(player));
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
            if ((event.getClickedInventory() == player.getInventory()) ||
                    (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY && !event.getClickedInventory().equals(player.getInventory()))) {
                event.setCancelled(true);
                if (guiItems.containsKey(event.getSlot())) {
                    InventoryGuiItem guiItem = guiItems.get(event.getSlot());
                    guiItem.raiseEvent(this, event);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (playerGuiHashMap.containsKey(event.getPlayer())) if (playerGuiHashMap.get(event.getPlayer()) == this) {
            if (guiItems.containsKey(event.getPlayer().getInventory().getHeldItemSlot())) {
                InventoryGuiItem guiItem = guiItems.get(event.getPlayer().getInventory().getHeldItemSlot());
                guiItem.raiseInteractEvent(this, event);
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

    void raiseInventoryCloseEvent(final Player player) {
        guiEvent.onClose(this, player);
    }

    void raiseInventoryOpenEvent(final Player player) {
        guiEvent.onOpen(this, player);
    }


    void stopTick(final Player player) {
        if (taskID.containsKey(player))
            Bukkit.getScheduler().cancelTask(taskID.get(player));
        taskID.remove(player);
    }

    public HashMap<Integer, InventoryGuiItem> getGuiItems() {
        return guiItems;
    }
}
