package com.gitlab.codedoctorde.api.ui.inventory;

import com.gitlab.codedoctorde.api.utils.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * @author CodeDoctorDE
 */
public class InventoryGuiItem {
    private ItemStack itemStack;
    private InventoryGuiItemEvent guiItemEvent;

    public InventoryGuiItem(final ItemStack itemStack, final InventoryGuiItemEvent guiItemEvent) {
        this.itemStack = itemStack;
        this.guiItemEvent = guiItemEvent;
    }

    public InventoryGuiItem(final ItemStackBuilder itemStackBuilder, final InventoryGuiItemEvent guiItemEvent) {
        this.itemStack = itemStackBuilder.build();
        this.guiItemEvent = guiItemEvent;
    }

    public InventoryGuiItem(final InventoryGuiItemEvent guiItemEvent) {
        this.itemStack = new ItemStack(Material.AIR);
        this.guiItemEvent = guiItemEvent;
    }

    public InventoryGuiItem(final ItemStack itemStack) {
        this.itemStack = itemStack;
        this.guiItemEvent = new InventoryGuiItemEvent() {
        };
    }

    public InventoryGuiItem(final ItemStackBuilder itemStackBuilder) {
        this.itemStack = itemStackBuilder.build();
        this.guiItemEvent = new InventoryGuiItemEvent() {
        };
    }

    public void raiseEvent(final InventoryGui gui, final InventoryClickEvent event) {
        guiItemEvent.onEvent(gui, this, event);
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public void runTick(InventoryGui gui, Player player) {
        guiItemEvent.onTick(gui, this, player);
    }

    public void raiseInteractEvent(InventoryGui inventoryGui, PlayerInteractEvent event) {
        guiItemEvent.onInteract(inventoryGui, this, event);
    }
}
