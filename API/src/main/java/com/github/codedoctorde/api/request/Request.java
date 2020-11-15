package com.github.codedoctorde.api.request;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author CodeDoctorDE
 */
public abstract class Request<T, E extends Event> implements Listener {
    protected final JavaPlugin plugin;
    protected final Player player;
    protected final RequestEvent<T> requestEvent;
    private static final HashMap<UUID, Request> requests = new HashMap<>();

    public Request(final JavaPlugin plugin, final Player player, final RequestEvent<T> requestEvent) {
        this.requestEvent = requestEvent;
        this.player = player;
        this.plugin = plugin;
        if(requests.containsKey(player.getUniqueId()))
            requests.get(player.getUniqueId()).cancel();
        requests.put(player.getUniqueId(), this);
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void raise(T output) {
        Bukkit.getScheduler().runTask(plugin, () -> requestEvent.onEvent(player, output));
        unregister();
    }

    public void unregister() {
        HandlerList.unregisterAll(this);
        requests.remove(player.getUniqueId());
    }

    public void cancel() {
        requestEvent.onCancel(player);
        unregister();
    }

    public Player getPlayer() {
        return player;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    @EventHandler
    private void onPlayerInteract(PlayerInteractEvent event) {
        Player current = event.getPlayer();
        if (event.getAction() != Action.LEFT_CLICK_AIR)
            return;
        if(player.getUniqueId().equals(current.getUniqueId()))
            cancel();
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (requests.containsKey(event.getPlayer().getUniqueId()))
            requests.get(event.getPlayer().getUniqueId()).cancel();
    }
    public abstract void onEvent(E event);
}
