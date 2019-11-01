package com.gitlab.codedoctorde.api.ui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public interface GuiItemEvent {
    default void onEvent(Gui gui, GuiPage guiPage, GuiItem guiItem, InventoryClickEvent event) {
    }

    default void onTick(Gui gui, GuiPage guiPage, GuiItem guiItem, Player player) {

    }

    default boolean onItemChange(Gui gui, GuiPage guiPage, GuiItem guiItem, Player player, ItemStack change) {
        return false;
    }
}
