package com.gitlab.codedoctorde.api.ui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GuiItem {
    private ItemStack itemStack;
    private GuiItemEvent guiItemEvent;

    public GuiItem(final ItemStack itemStack, final GuiItemEvent guiItemEvent) {
        this.itemStack = itemStack;
        this.guiItemEvent = guiItemEvent;
    }

    public GuiItem(final GuiItemEvent guiItemEvent) {
        this.itemStack = new ItemStack(Material.AIR);
        this.guiItemEvent = guiItemEvent;
    }

    public GuiItem(final ItemStack itemStack) {
        this.itemStack = itemStack;
        this.guiItemEvent = new GuiItemEvent() {
        };
    }

    public void raiseEvent(final Gui gui, final InventoryClickEvent event) {
        guiItemEvent.onEvent(gui, this, event);
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public void runTick(Gui gui, Player player) {
        guiItemEvent.onTick(gui, this, player);
    }
}
