package com.github.codedoctorde.api.request;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

/**
 * @author CodeDoctorDE
 */
public class BlockPlaceRequest extends Request<Block, BlockPlaceEvent> {
    public BlockPlaceRequest(final JavaPlugin plugin, final Player player, final RequestEvent<Block> blockPlaceRequestEvent) {
        super(plugin, player, blockPlaceRequestEvent);
    }

    public void onEvent(BlockPlaceEvent event) {
        Player current = event.getPlayer();
        if(!player.getUniqueId().equals(current.getUniqueId()))
            return;
        raise(event.getBlockPlaced());
        event.setCancelled(true);
    }
}
