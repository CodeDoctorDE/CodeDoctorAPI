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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Gui implements Listener {
    static HashMap<Player, Gui> playerGuiHashMap = new HashMap<>();
    static HashMap<Player, Gui> playerInventoryHashMap = new HashMap<>();
    private final JavaPlugin plugin;
    List<GuiPage> guiPages = new ArrayList<>();
    int index = 0;
    private HashMap<Player, Integer> taskID = new HashMap<>();

    public Gui(JavaPlugin javaPlugin) {
        plugin = javaPlugin;
        Bukkit.getPluginManager().registerEvents(this, javaPlugin);
    }

    public void open(final Player player) {
        player.closeInventory();
        playerGuiHashMap.put(player, this);
        final Inventory inventory = guiPages.get(index).build();
        player.openInventory(inventory);
        startTick(player);
        getCurrentGuiPage().raiseInventoryOpenEvent(this, player);
    }

    public GuiPage getCurrentGuiPage() {
        if (index >= 0 && index < guiPages.size())
            return guiPages.get(index);
        else
            return null;
    }

    public List<GuiPage> getGuiPages() {
        return guiPages;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
        playerGuiHashMap.keySet().forEach(this::open);
    }

    public void setInventoryIndex(int index) {
        this.index = index;
        playerGuiHashMap.keySet().forEach(this::openInventory);
    }

    public boolean nextIndex() {
        if (guiPages.size() <= 1)
            return false;
        index++;
        if (index >= guiPages.size()) {
            index = guiPages.size() - 1;
            return false;
        }
        playerGuiHashMap.keySet().forEach(this::open);
        return true;
    }

    public boolean nextInventoryIndex() {
        if (guiPages.size() <= 1)
            return false;
        index++;
        if (index >= guiPages.size()) {
            index = guiPages.size() - 1;
            return false;
        }
        playerGuiHashMap.keySet().forEach(this::openInventory);
        return true;
    }

    public boolean previousIndex() {
        if (guiPages.size() <= 1)
            return false;
        if (index >= guiPages.size()) {
            index = guiPages.size() - 1;
            return false;
        }
        index--;
        if (index < 0) {
            index = 0;
            return false;
        }
        playerGuiHashMap.keySet().forEach(this::open);
        return true;
    }

    public boolean previousInventoryIndex() {
        if (guiPages.size() <= 1)
            return false;
        if (index >= guiPages.size()) {
            index = guiPages.size() - 1;
            return false;
        }
        index--;
        if (index < 0) {
            index = 0;
            return false;
        }
        playerGuiHashMap.keySet().forEach(this::openInventory);
        return true;
    }

    public boolean firstIndex() {
        if (index <= 0) {
            index = 0;
            return false;
        }
        if (guiPages.size() <= 1)
            return false;
        index = 0;
        playerGuiHashMap.keySet().forEach(this::open);
        return true;
    }

    public boolean firstInventoryIndex() {
        if (index <= 0) {
            index = 0;
            return false;
        }
        if (guiPages.size() <= 1)
            return false;
        index = 0;
        playerGuiHashMap.keySet().forEach(this::openInventory);
        return true;
    }

    public boolean lastIndex() {
        if (guiPages.size() <= 1)
            return false;
        if (index + 1 >= guiPages.size()) {
            index = guiPages.size() - 1;
            return false;
        }
        index = guiPages.size() - 1;
        playerGuiHashMap.keySet().forEach(this::open);
        return true;
    }

    public boolean lastInventoryIndex() {
        if (guiPages.size() <= 1)
            return false;
        if (index + 1 >= guiPages.size()) {
            index = guiPages.size() - 1;
            return false;
        }
        index = guiPages.size() - 1;
        playerGuiHashMap.keySet().forEach(this::openInventory);
        return true;
    }

    public void sync(final Player player) {
        if (!playerGuiHashMap.containsKey(player))
            return;
        player.getOpenInventory().getTopInventory().clear();
        playerGuiHashMap.get(player).getCurrentGuiPage().getGuiItems().keySet().forEach(key -> player.getOpenInventory().getTopInventory().setItem(key, playerGuiHashMap.get(player).getCurrentGuiPage().getGuiItems().get(key).getItemStack()));
    }

    public void syncInventory(final Player player) {
        if (!playerInventoryHashMap.containsKey(player))
            return;
        playerInventoryHashMap.get(player).getCurrentGuiPage().getGuiItems().keySet().forEach(key -> player.getInventory().setItem(key, playerInventoryHashMap.get(player).getCurrentGuiPage().getGuiItems().get(key).getItemStack()));
    }

    public void close(Player... players) {
        for (Player player :
                players) {
            if (playerGuiHashMap.containsKey(player)) {
                playerGuiHashMap.get(player).getCurrentGuiPage().raiseInventoryCloseEvent(this, player);
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
        if (playerGuiHashMap.containsKey(player)) {
            if (playerGuiHashMap.get(player) == this) {
                if (event.getInventory().equals(event.getView().getTopInventory()))
                    event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player))
            return;
        Player player = (Player) event.getWhoClicked();
        if (playerGuiHashMap.containsKey(player)) if (playerGuiHashMap.get(player) == this) {
            Gui gui = playerGuiHashMap.get(player);
            if ((event.getClickedInventory() != player.getInventory()) ||
                    (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY && event.getInventory() == player.getInventory())) {
                event.setCancelled(true);
                if (getCurrentGuiPage().getGuiItems().containsKey(event.getSlot())) {
                    GuiItem guiItem = getCurrentGuiPage().getGuiItems().get(event.getSlot());
                    if (guiItem.raiseItemChangeEvent(gui, getCurrentGuiPage(), player, event.getCurrentItem())) {
                        guiItem.setItemStack(event.getCursor());
                        event.setCurrentItem(event.getCursor());
                        player.setItemOnCursor(null);
                    }
                    guiItem.raiseEvent(this, getCurrentGuiPage(), player, event.getClick());
                }
            }
        }
    }

    public void startTick(Player player) {
        taskID.put(player, Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> runTick(player), 1, 1));
    }

    private void runTick(Player player) {
        if (getCurrentGuiPage() != null)
            getCurrentGuiPage().runTick(this, player);
    }


    void stopTick(final Player player) {
        if (taskID.containsKey(player))
            Bukkit.getScheduler().cancelTask(taskID.get(player));
        taskID.remove(player);
    }


    public void openInventory(final Player player) {
        playerInventoryHashMap.put(player, this);
        final Inventory inventory = guiPages.get(index).build(player.getInventory());
        startTick(player);
        getCurrentGuiPage().raiseInventoryOpenEvent(this, player);
    }

    public void closeInventory(final Player player) {
        if (playerInventoryHashMap.containsKey(player)) {
            playerInventoryHashMap.get(player).getCurrentGuiPage().raiseInventoryCloseEvent(this, player);
            stopTick(player);
            playerInventoryHashMap.remove(player);
        }
        player.closeInventory();
    }
}
