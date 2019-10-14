package com.gitlab.codedoctorde.api.ui;

import org.bukkit.entity.Player;

public interface GuiEvent {
    void onTick(Gui gui, GuiPage guiPage, Player player);

    void onOpen(Gui gui, GuiPage guiPage, Player player);

    void onClose(Gui gui, GuiPage guiPage, Player player);
}
