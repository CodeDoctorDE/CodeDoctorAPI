package com.github.codedoctorde.api.request;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public interface BlockBreakRequestEvent extends RequestEvent {
    void onEvent(final Player player, final Block output);

    void onCancel(final Player player);
}
