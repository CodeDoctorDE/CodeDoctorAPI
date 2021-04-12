package com.github.codedoctorde.api.request;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author CodeDoctorDE
 */
public class ChatRequest extends Request<String, AsyncPlayerChatEvent> {
    public ChatRequest(final JavaPlugin plugin, final Player player, final RequestEvent<String> chatRequestEvent) {
        super(plugin, player, chatRequestEvent);
    }

    @EventHandler
    public void onEvent(AsyncPlayerChatEvent event) {
        Player current = event.getPlayer();
        if(!player.getUniqueId().equals(current.getUniqueId()))
            return;
        raise(event.getMessage());
        event.setCancelled(true);
    }
}
