package com.github.codedoctorde.api.ui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public interface GuiItem {
    ItemStack build(ChestGui gui);
    void onClick(ChestGui gui, InventoryClickEvent event);
    void onTick(ChestGui gui);
}
