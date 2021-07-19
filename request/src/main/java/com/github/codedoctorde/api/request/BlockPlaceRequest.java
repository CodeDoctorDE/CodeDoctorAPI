package com.github.codedoctorde.api.request;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * @author CodeDoctorDE
 */
public class BlockPlaceRequest extends Request<Block> {
    public BlockPlaceRequest(final @NotNull Player player) {
        super(player);
    }
}
