package com.gitlab.codedoctorde.api.request;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class ItemRequestEvent {
    public abstract void onEvent(final Player player, final ItemStack itemStack);

    public abstract void onCancel(final Player player);
}
