package com.gitlab.codedoctorde.api.request;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public interface BlockPlaceRequestEvent extends RequestEvent {
    void onEvent(final Player player, final Block output);
}
