package com.gitlab.codedoctorde.api.ui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class GuiItem {
    private boolean canSteal = false;
    private boolean canChange = false;
    private ItemStack itemStack;
    private GuiItemEvent guiItemEvent;

    public GuiItem(final ItemStack itemStack, final GuiItemEvent guiItemEvent) {
        this.itemStack = itemStack;
        this.guiItemEvent = guiItemEvent;
    }

    public GuiItem(final ItemStack itemStack, final boolean canSteal, final boolean canChange, final GuiItemEvent guiItemEvent) {
        this.canSteal = canSteal;
        this.canChange = canChange;
        this.itemStack = itemStack;
        this.guiItemEvent = guiItemEvent;
    }

    public GuiItem(final GuiItemEvent guiItemEvent) {
        this.itemStack = new ItemStack(Material.AIR);
        this.guiItemEvent = guiItemEvent;
    }

    public GuiItem(final boolean canSteal, final boolean canChange, final GuiItemEvent guiItemEvent) {
        this.canSteal = canSteal;
        this.canChange = canChange;
        this.itemStack = new ItemStack(Material.AIR);
        this.guiItemEvent = guiItemEvent;
    }

    public void raiseEvent(final Gui gui, final GuiPage guiPage, final Player player, final ClickType clickType) {
        guiItemEvent.onEvent(gui, guiPage, this, player, clickType);
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public void runTick(Gui gui, GuiPage guiPage, Player player) {
        guiItemEvent.onTick(gui, guiPage, this, player);
    }

    public boolean isCanChange() {
        return canChange;
    }

    public void setCanChange(boolean canChange) {
        this.canChange = canChange;
    }

    public boolean isCanSteal() {
        return canSteal;
    }

    public void setCanSteal(boolean canSteal) {
        this.canSteal = canSteal;
    }
}
