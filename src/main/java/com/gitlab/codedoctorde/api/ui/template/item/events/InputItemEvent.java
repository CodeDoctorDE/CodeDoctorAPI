package com.gitlab.codedoctorde.api.ui.template.item.events;

import com.gitlab.codedoctorde.api.ui.Gui;
import com.gitlab.codedoctorde.api.ui.GuiItem;
import com.gitlab.codedoctorde.api.ui.template.item.InputItem;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public interface InputItemEvent {
    default void onEvent(Gui gui, GuiItem guiItem, InventoryClickEvent event, InputItem inputItem) {
    }

    default void onTick(Gui gui, GuiItem guiItem, Player player, InputItem inputItem) {

    }
}
