package com.github.codedoctorde.api.ui;

import org.bukkit.entity.Player;

public interface GuiEvent {
    default void onTick(ChestGui gui, Player player) {

    }

    default void onOpen(ChestGui gui, Player player) {

    }

    default void onClose(ChestGui gui, Player player) {

    }
}
