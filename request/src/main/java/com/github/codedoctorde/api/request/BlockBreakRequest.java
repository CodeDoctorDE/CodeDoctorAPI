package com.github.codedoctorde.api.request;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class BlockBreakRequest extends Request<Block> {
    public BlockBreakRequest(final Player player) {
        super(player);
    }
}
