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
    private static HashMap<Player, Gui> playerGuiHashMap = new HashMap<>();
    private List<GuiPage> guiPages = new ArrayList<>();
    private int index = 0;

    public Gui(JavaPlugin javaPlugin) {
        Bukkit.getPluginManager().registerEvents(this, javaPlugin);
    }

    public void open(final Player player) {
        player.closeInventory();
        playerGuiHashMap.put(player, this);
        final Inventory inventory = guiPages.get(index).build();
        player.openInventory(inventory);
        getCurrentGuiPage().startTick(this, player);
        getCurrentGuiPage().raiseInventoryOpenEvent(this, player);
    }

    public GuiPage getCurrentGuiPage() {
        return guiPages.get(index);
    }

    public List<GuiPage> getGuiPages() {
        return guiPages;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
        for (Player player :
                playerGuiHashMap.keySet()) {
            open(player);
        }
    }

    public boolean nextIndex() {
        if (guiPages.size() <= 1)
            return false;
        index++;
        if (index >= guiPages.size()) {
            index = guiPages.size() - 1;
            return false;
        }
        for (Player player :
                playerGuiHashMap.keySet()) {
            open(player);
        }
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
        for (Player player :
                playerGuiHashMap.keySet()) {
            open(player);
        }
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
        for (Player player :
                playerGuiHashMap.keySet()) {
            open(player);
        }
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
        for (Player player :
                playerGuiHashMap.keySet()) {
            open(player);
        }
        return true;
    }

    public void sync(final Player player) {
        if (!playerGuiHashMap.containsKey(player))
            return;
        player.getOpenInventory().getTopInventory().clear();
        for (int key :
                playerGuiHashMap.get(player).getCurrentGuiPage().getGuiItems().keySet()) {
            player.getOpenInventory().getTopInventory().setItem(key, playerGuiHashMap.get(player).getCurrentGuiPage().getGuiItems().get(key).getItemStack());
        }
    }

    public void close(Player player) {
        if (playerGuiHashMap.containsKey(player)) {
            playerGuiHashMap.get(player).getCurrentGuiPage().raiseInventoryCloseEvent(this, player);
            playerGuiHashMap.get(player).getCurrentGuiPage().stopTick(player);
            playerGuiHashMap.remove(player);
        }
        player.closeInventory();
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
        if (playerGuiHashMap.containsKey(player)) {
            if (playerGuiHashMap.get(player) == this) {
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
    }

}
