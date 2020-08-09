package com.github.codedoctorde.api.ui.inventory;

import org.bukkit.entity.Player;

public interface InventoryGuiEvent {
    default void onTick(InventoryGui gui, Player player) {

    }

    default void onOpen(InventoryGui gui, Player player) {

    }

    default void onClose(InventoryGui gui, Player player) {

    }
}
