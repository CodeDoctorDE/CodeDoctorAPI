package com.gitlab.codedoctorde.api.ui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public abstract class GuiItemEvent {
    void onEvent(Gui gui, GuiPage guiPage, GuiItem guiItem, Player player, ClickType clickType) {

    }

    void onTick(Gui gui, GuiPage guiPage, GuiItem guiItem, Player player) {

    }

    boolean onItemChange(Gui gui, GuiPage guiPage, GuiItem guiItem, Player player, ItemStack change) {
        return false;
    }
}
