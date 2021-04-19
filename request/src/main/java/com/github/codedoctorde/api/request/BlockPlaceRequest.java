package com.github.codedoctorde.api.request;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author CodeDoctorDE
 */
public class BlockPlaceRequest extends Request<Block> {
    public BlockPlaceRequest(final Player player) {
        super(player);
    }
}
