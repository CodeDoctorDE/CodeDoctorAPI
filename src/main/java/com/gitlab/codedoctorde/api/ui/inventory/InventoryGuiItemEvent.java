package com.gitlab.codedoctorde.api.ui.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public interface InventoryGuiItemEvent {
    default void onEvent(InventoryGui gui, InventoryGuiItem guiItem, InventoryClickEvent event) {
    }

    default void onTick(InventoryGui gui, InventoryGuiItem guiItem, Player player) {

    }

    default boolean onItemChange(InventoryGui gui, InventoryGuiItem guiItem, Player player, ItemStack change) {
        return false;
    }
}
