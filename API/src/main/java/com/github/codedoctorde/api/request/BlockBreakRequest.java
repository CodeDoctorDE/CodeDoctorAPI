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

public class BlockBreakRequest extends Request<BlockBreakRequestEvent> {
    private static final HashMap<Player, BlockBreakRequest> requests = new HashMap<>();

    public BlockBreakRequest(final JavaPlugin plugin, final Player player, final BlockBreakRequestEvent blockBreakRequestEvent) {
        super(plugin, player, blockBreakRequestEvent);
        if (requests.containsKey(player))
            requests.get(player).cancel();
        requests.remove(player);
        requests.put(player, this);
    }

    public void event(Player player, Block output) {
        requestEvent.onEvent(player, output);
    }

    @EventHandler
    private void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (requests.containsKey(player)) {
            Bukkit.getScheduler().runTask(plugin, () -> {
                if (requests.containsKey(player))
                    requests.get(player).event(player, event.getBlock());
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
