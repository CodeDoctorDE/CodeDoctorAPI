package com.github.codedoctorde.api.request;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Objects;

/**
 * @author CodeDoctorDE
 */
public class ItemRequest extends Request<ItemStack, PlayerDropItemEvent> {
    private static final HashMap<Player, ItemRequest> requests = new HashMap<>();

    public ItemRequest(final JavaPlugin plugin, final Player player, final RequestEvent<ItemStack> requestEvent) {
        super(plugin, player, requestEvent);
    }

    @EventHandler
    public void onEvent(PlayerDropItemEvent event) {
        Player current = event.getPlayer();
        if(!player.getUniqueId().equals(current.getUniqueId()))
            return;
        raise(event.getItemDrop().getItemStack());
        event.setCancelled(true);
    }
}
