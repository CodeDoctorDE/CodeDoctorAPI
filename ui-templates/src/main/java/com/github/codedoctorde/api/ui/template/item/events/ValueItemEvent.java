package com.github.codedoctorde.api.ui.template.item.events;

import com.github.codedoctorde.api.ui.template.item.ValueItem;
import com.github.codedoctorde.api.ui.ChestGui;
import com.github.codedoctorde.api.ui.StaticItem;
import org.bukkit.entity.Player;

/**
 * @author CodeDoctorDE
 */
public interface ValueItemEvent {
    boolean onEvent(float value, Player player, ValueItem valueItem);

    default void onTick(ChestGui gui, StaticItem staticItem, Player player) {

    }
}
