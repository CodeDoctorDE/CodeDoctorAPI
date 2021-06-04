package com.github.codedoctorde.api.request;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
