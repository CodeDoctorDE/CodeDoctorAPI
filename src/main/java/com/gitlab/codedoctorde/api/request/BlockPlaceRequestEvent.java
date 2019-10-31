package com.gitlab.codedoctorde.api.request;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public abstract class BlockPlaceRequestEvent {
    public void onEvent(final Player player, final Block output) {

    }

    public void onCancel(final Player player) {

    }
}
