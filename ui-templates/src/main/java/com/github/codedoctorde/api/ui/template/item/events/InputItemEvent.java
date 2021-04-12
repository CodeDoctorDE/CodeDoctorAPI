package com.github.codedoctorde.api.ui.template.item.events;

import com.github.codedoctorde.api.ui.template.item.InputItem;
import com.github.codedoctorde.api.ui.ChestGui;
import com.github.codedoctorde.api.ui.StaticItem;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public interface InputItemEvent {
    default void onEvent(ChestGui gui, StaticItem staticItem, InventoryClickEvent event, InputItem inputItem) {
    }

    default void onTick(ChestGui gui, StaticItem staticItem, Player player, InputItem inputItem) {

    }
}
