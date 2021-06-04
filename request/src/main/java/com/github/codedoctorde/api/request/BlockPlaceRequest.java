package com.github.codedoctorde.api.request;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 * @author CodeDoctorDE
 */
public class BlockPlaceRequest extends Request<Block> {
    public BlockPlaceRequest(final Player player) {
        super(player);
    }
}
