package com.github.codedoctorde.api.ui.template.item.events;

import com.github.codedoctorde.api.ui.template.item.ValueItem;
import com.github.codedoctorde.api.ui.Gui;
import com.github.codedoctorde.api.ui.GuiItem;
import org.bukkit.entity.Player;

/**
 * @author CodeDoctorDE
 */
public interface ValueItemEvent {
    boolean onEvent(float value, Player player, ValueItem valueItem);

    default void onTick(Gui gui, GuiItem guiItem, Player player) {

    }
}
