package com.gitlab.codedoctorde.api.ui;

import org.bukkit.entity.Player;

public interface GuiEvent {
    default void onTick(Gui gui, GuiPage guiPage, Player player) {

    }

    default void onOpen(Gui gui, GuiPage guiPage, Player player) {

    }

    default void onClose(Gui gui, GuiPage guiPage, Player player) {

    }
}
