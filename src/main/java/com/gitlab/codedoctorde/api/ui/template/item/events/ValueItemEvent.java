package com.gitlab.codedoctorde.api.ui.template.item.events;

import com.gitlab.codedoctorde.api.ui.Gui;
import com.gitlab.codedoctorde.api.ui.GuiItem;
import com.gitlab.codedoctorde.api.ui.template.item.ValueItem;
import org.bukkit.entity.Player;

/**
 * @author CodeDoctorDE
 */
public interface ValueItemEvent {
    boolean onEvent(float value, Player player, ValueItem valueItem);

    default void onTick(Gui gui, GuiItem guiItem, Player player) {

    }
}
