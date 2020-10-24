package com.github.codedoctorde.api.request;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

/**
 * @author CodeDoctorDE
 */
public class ChatRequest extends Request<ChatRequestEvent> {
    private static final HashMap<Player, ChatRequest> requests = new HashMap<>();

    public ChatRequest(final JavaPlugin plugin, final Player player, final ChatRequestEvent chatRequestEvent) {
        super(plugin, player, chatRequestEvent);
        if (requests.containsKey(player))
            requests.get(player).cancel();
        requests.remove(player);
        requests.put(player, this);
    }

    public void event(Player player, String output) {
        requestEvent.onEvent(player, output);
        unregister();
    }

    public void unregister() {
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    private void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (requests.containsKey(player)) {
            Bukkit.getScheduler().runTask(plugin, () -> {
                if (requests.containsKey(player))
                    requests.get(player).event(player, event.getMessage());
                requests.remove(player);
            });
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() != Action.LEFT_CLICK_AIR)
            return;
        if (requests.containsKey(player)) {
            event.setCancelled(true);
            requests.get(player).cancel();
            requests.remove(player);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (requests.containsKey(event.getPlayer()))
            requests.get(event.getPlayer()).cancel();
        requests.remove(event.getPlayer());
    }
}
