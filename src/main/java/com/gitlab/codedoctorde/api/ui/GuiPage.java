package com.gitlab.codedoctorde.api.ui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class GuiPage {
    private String title;
    private int size;
    private HashMap<Integer, GuiItem> guiItems = new HashMap<>();
    private GuiEvent guiEvent;
    private Inventory inventory = null;

    public GuiPage(final String title, final int size, final GuiEvent guiEvent) {
        this.guiEvent = guiEvent;
        this.title = title;
        this.size = size;
    }

    public GuiPage(final String title, final int size) {
        this.title = title;
        this.size = size;
        this.guiEvent = new GuiEvent() {
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

    private void raiseEvent(final Gui gui, final GuiItem guiItem, final InventoryClickEvent event) {
        guiItem.raiseEvent(gui, this, event);
    }

    void raiseItemEvent(final Gui gui, final int location, final InventoryClickEvent event) {
        for (int key :
                guiItems.keySet())
            if (key == location)
                raiseEvent(gui, guiItems.get(key), event);
    }

    public void runTick(final Gui gui, final Player player) {
        guiEvent.onTick(gui, this, player);
        for (GuiItem guiItem :
                guiItems.values())
            guiItem.runTick(gui, this, player);
    }

    int getSize() {
        return size;
    }

    public Inventory build() {
        return build(inventory);
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

    void raiseInventoryCloseEvent(final Gui gui, final Player player) {
        guiEvent.onClose(gui, this, player);
    }

    void raiseInventoryOpenEvent(final Gui gui, final Player player) {
        guiEvent.onOpen(gui, this, player);
    }

}

