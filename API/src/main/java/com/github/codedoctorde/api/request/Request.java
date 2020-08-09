package com.github.codedoctorde.api.request;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author CodeDoctorDE
 */
public abstract class Request<T extends RequestEvent> implements Listener {
    protected final JavaPlugin plugin;
    protected final Player player;
    protected final T requestEvent;

    public Request(final JavaPlugin plugin, final Player player, final T requestEvent) {
        this.requestEvent = requestEvent;
        this.player = player;
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void unregister() {
        HandlerList.unregisterAll(this);
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
}
