package com.gitlab.codedoctorde.api.ui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class GuiPage {
    private String title;
    private int size;
    private HashMap<Integer, GuiItem> guiItems = new HashMap<>();
    private GuiEvent guiEvent;
    private HashMap<Player, Integer> taskID = new HashMap<>();
    private JavaPlugin javaPlugin;
    private Inventory inventory = null;

    public GuiPage(final JavaPlugin javaPlugin, final String title, final int size, final GuiEvent guiEvent) {
        this.javaPlugin = javaPlugin;
        this.guiEvent = guiEvent;
        this.title = title;
        this.size = size;
    }

    public GuiPage(final JavaPlugin javaPlugin, final String title, final int size) {
        this.javaPlugin = javaPlugin;
        this.title = title;
        this.size = size;
        this.guiEvent = new GuiEvent() {
            @Override
            public void onTick(Gui gui, GuiPage guiPage, Player player) {

            }

            @Override
            public void onOpen(Gui gui, GuiPage guiPage, Player player) {

            }

            @Override
            public void onClose(Gui gui, GuiPage guiPage, Player player) {

            }
        };
    }

    /*public Inventory build(){
        Inventory inventory = Bukkit.createInventory(null, size, title);
        for (GuiItem guiItem:
             guiItems)
            inventory.setItem(guiItem.getLocation(), guiItem.getItemStack());
        return inventory;
    }*/

    protected HashMap<Integer, GuiItem> getGuiItems() {
        return guiItems;
    }

    String getTitle() {
        return title;
    }

    private void raiseEvent(final Gui gui, final Player player, final GuiItem guiItem, final ClickType clickType) {
        guiItem.raiseEvent(gui, this, player, clickType);
    }

    void raiseItemEvent(final Gui gui, final Player player, final int location, final ClickType clickType) {
        for (int key :
                guiItems.keySet())
            if (key == location)
                raiseEvent(gui, player, guiItems.get(key), clickType);
    }

    private void runTick(final Gui gui, final Player player) {
        guiEvent.onTick(gui, this, player);
        for (GuiItem guiItem :
                guiItems.values())
            guiItem.runTick(gui, this, player);
    }

    void startTick(final Gui gui, final Player player) {
        taskID.put(player, Bukkit.getScheduler().scheduleSyncRepeatingTask(javaPlugin, () -> runTick(gui, player), 1, 1));
    }

    void stopTick(final Player player) {
        if (taskID.containsKey(player))
            Bukkit.getScheduler().cancelTask(taskID.get(player));
        taskID.remove(player);
    }

    int getSize() {
        return size;
    }

    public Inventory build() {
        if (inventory == null) inventory = Bukkit.createInventory(null, size * 9, title);
        inventory.clear();
        for (int key : guiItems.keySet()) inventory.setItem(key, guiItems.get(key).getItemStack());
        return inventory;
    }

    public Inventory buildNew() {
        inventory = Bukkit.createInventory(null, size * 9, title);
        inventory.clear();
        for (int key : guiItems.keySet()) inventory.setItem(key, guiItems.get(key).getItemStack());
        return inventory;
    }

    void raiseInventoryCloseEvent(final Gui gui, final Player player) {
        guiEvent.onClose(gui, this, player);
    }

    void raiseInventoryOpenEvent(final Gui gui, final Player player) {
        guiEvent.onOpen(gui, this, player);
    }

}

