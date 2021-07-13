package com.github.codedoctorde.api.request;

import org.bukkit.entity.Player;

/**
 * @author CodeDoctorDE
 */
public class ChatRequest extends Request<String> {
    public ChatRequest(final Player player) {
        super(player);
    }
}
