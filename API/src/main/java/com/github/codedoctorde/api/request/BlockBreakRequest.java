package com.github.codedoctorde.api.request;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class BlockBreakRequest extends Request<Block, BlockBreakEvent> {
    public BlockBreakRequest(final JavaPlugin plugin, final Player player, final RequestEvent<Block> blockBreakRequestEvent) {
        super(plugin, player, blockBreakRequestEvent);
    }

    @EventHandler
    public void onEvent(BlockBreakEvent event) {
        Player current = event.getPlayer();
        if(!player.getUniqueId().equals(current.getUniqueId()))
            return;
        raise(event.getBlock());
        event.setCancelled(true);
    }
}
