package com.github.codedoctorde.api.ui;

import com.github.codedoctorde.api.utils.ItemStackBuilder;
import com.google.gson.JsonObject;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

/**
 * @author CodeDoctorDE
 */
public class StaticItem implements GuiItem {
    private ItemStack itemStack;
    private Consumer<InventoryClickEvent> clickAction;

    public StaticItem(final ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public void raiseEvent(final InventoryClickEvent event) {
        clickAction.accept(event);
    }

    public void setClickAction(Consumer<InventoryClickEvent> clickAction) {
        this.clickAction = clickAction;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public ItemStack build() {
        return getItemStack();
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        clickAction.accept(event);
    }
}
