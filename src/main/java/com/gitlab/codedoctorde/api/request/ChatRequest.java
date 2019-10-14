package com.gitlab.codedoctorde.api.request;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class ChatRequest implements Listener {
    private static HashMap<Player, ChatRequest> requests = new HashMap<>();
    private final JavaPlugin plugin;
    private ChatRequestEvent chatRequestEvent;

    public ChatRequest(final JavaPlugin plugin, final Player player, final ChatRequestEvent chatRequestEvent) {
        this.chatRequestEvent = chatRequestEvent;
        if (requests.containsKey(player))
            requests.get(player).cancel(player);
        requests.remove(player);
        requests.put(player, this);
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void cancel(Player player) {
        chatRequestEvent.onCancel(player);
    }

    public void event(Player player, String output) {
        chatRequestEvent.onEvent(player, output);
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
    private void onPlayerMove(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() != Action.LEFT_CLICK_AIR)
            return;
        if (requests.containsKey(player)) {
            event.setCancelled(true);
            requests.get(player).cancel(player);
            requests.remove(player);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (requests.containsKey(event.getPlayer()))
            requests.get(event.getPlayer()).cancel(event.getPlayer());
        requests.remove(event.getPlayer());
    }
}
