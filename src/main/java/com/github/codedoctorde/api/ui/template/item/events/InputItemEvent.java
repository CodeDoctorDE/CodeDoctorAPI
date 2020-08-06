package com.github.codedoctorde.api.ui.template.item.events;

import com.github.codedoctorde.api.ui.template.item.InputItem;
import com.github.codedoctorde.api.ui.Gui;
import com.github.codedoctorde.api.ui.GuiItem;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public interface InputItemEvent {
    default void onEvent(Gui gui, GuiItem guiItem, InventoryClickEvent event, InputItem inputItem) {
    }

    default void onTick(Gui gui, GuiItem guiItem, Player player, InputItem inputItem) {

    }
}
