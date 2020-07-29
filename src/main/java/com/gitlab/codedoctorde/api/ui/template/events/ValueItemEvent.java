package com.gitlab.codedoctorde.api.ui.template.events;

import com.gitlab.codedoctorde.api.ui.template.ValueItem;
import org.bukkit.entity.Player;

/**
 * @author CodeDoctorDE
 */
public interface ValueItemEvent {
    boolean onEvent(float value, Player player, ValueItem valueItem);
}
