package com.github.codedoctorde.api.request;

import org.bukkit.entity.Player;

/**
 * @author CodeDoctorDE
 */
public interface RequestEvent {
    void onCancel(Player player);
}
