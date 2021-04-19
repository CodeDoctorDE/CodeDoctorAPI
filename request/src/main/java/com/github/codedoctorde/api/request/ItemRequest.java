package com.github.codedoctorde.api.request;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

/**
 * @author CodeDoctorDE
 */
public class ItemRequest extends Request<ItemStack> {
    private static final HashMap<Player, ItemRequest> requests = new HashMap<>();

    public ItemRequest(final Player player) {
        super(player);
    }
}
