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
public class ItemRequest extends Request<ItemRequestEvent> {
    private static HashMap<Player, ItemRequest> requests = new HashMap<>();

    public ItemRequest(final JavaPlugin plugin, final Player player, final ItemRequestEvent itemRequestEvent) {
        super(plugin, player, itemRequestEvent);
        if (requests.containsKey(player))
            requests.get(player).cancel();
        requests.remove(player);
        requests.put(player, this);
    }

    public void event(Player player, ItemStack itemStack) {
        requestEvent.onEvent(player, itemStack);
        unregister();
    }

    public void unregister() {
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    private void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (requests.containsKey(player)) {
            Bukkit.getScheduler().runTask(plugin, () -> {
                if (requests.containsKey(player))
                    requests.get(player).event(player, event.getItemDrop().getItemStack());
                requests.remove(player);
            });
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (event.getFrom().getBlockX() != Objects.requireNonNull(event.getTo()).getBlockX() || event.getFrom().getBlockZ() != event.getTo().getBlockZ() || event.getFrom().getBlockY() != event.getTo().getBlockY())
            if (requests.containsKey(player)) {
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
