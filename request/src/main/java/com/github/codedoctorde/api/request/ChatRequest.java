package com.github.codedoctorde.api.request;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author CodeDoctorDE
 */
public class ChatRequest extends Request<String> {
    public ChatRequest(final Player player) {
        super(player);
    }
}
