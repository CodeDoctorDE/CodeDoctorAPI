package com.gitlab.codedoctorde.api.ui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public interface GuiItemEvent {
    void onEvent(Gui gui, GuiPage guiPage, GuiItem guiItem, Player player, ClickType clickType);

    void onTick(Gui gui, GuiPage guiPage, GuiItem guiItem, Player player);
}
