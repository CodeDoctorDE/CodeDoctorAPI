package com.github.codedoctorde.api.ui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public interface GuiItem {
    ItemStack build();
    void onClick(InventoryClickEvent event);
    void onTick();
}
