package com.gitlab.codedoctorde.api.request;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface ItemRequestEvent extends RequestEvent {
    void onEvent(final Player player, final ItemStack itemStack);
}
