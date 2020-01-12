package com.gitlab.codedoctorde.api.ui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;


/**
 * The gui class is obsoleted!
 *
 * @deprecated Please use the class
 * {@link Gui}!
 */
@Deprecated
/**
 * @author CodeDoctorDE
 */
public class GuiPage {
    private JavaPlugin plugin;
    private String title;
    private int size;
    private HashMap<Integer, GuiItem> guiItems = new HashMap<>();
    private GuiEvent guiEvent;
    private Inventory inventory = null;

    @Deprecated
    public GuiPage(final String title, final int size, final GuiEvent guiEvent) {
        this.guiEvent = guiEvent;
        this.title = title;
        this.size = size;
    }

    @Deprecated
    public GuiPage(final String title, final int size) {
        this.title = title;
        this.size = size;
        this.guiEvent = new GuiEvent() {
        };
    }

    public GuiPage(final JavaPlugin javaPlugin, final String title, final int size) {
        this.title = title;
        this.size = size;
        this.plugin = javaPlugin;
        this.guiEvent = new GuiEvent() {
        };
    }

    public GuiPage(final JavaPlugin javaPlugin, final String title, final int size, final GuiEvent guiEvent) {
        this.guiEvent = guiEvent;
        this.title = title;
        this.plugin = javaPlugin;
        this.size = size;
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
        guiItem.raiseEvent(gui, event);
    }

    void raiseItemEvent(final Gui gui, final int location, final InventoryClickEvent event) {
        for (int key :
                guiItems.keySet())
            if (key == location)
                raiseEvent(gui, guiItems.get(key), event);
    }

    public void runTick(final Gui gui, final Player player) {
        guiEvent.onTick(gui, player);
        for (GuiItem guiItem :
                guiItems.values())
            guiItem.runTick(gui, player);
    }

    int getSize() {
        return size;
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

    void raiseInventoryCloseEvent(final Gui gui, final Player player) {
        guiEvent.onClose(gui, player);
    }

    void raiseInventoryOpenEvent(final Gui gui, final Player player) {
        guiEvent.onOpen(gui, player);
    }

}

