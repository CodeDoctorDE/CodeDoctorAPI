package com.github.codedoctorde.api.ui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public interface GuiItem {
    ItemStack build(Gui gui);
    void onClick(InventoryClickEvent event);
    default void onOpen(Player player){

    }
    default void onClose(Player player) {

    }
}
