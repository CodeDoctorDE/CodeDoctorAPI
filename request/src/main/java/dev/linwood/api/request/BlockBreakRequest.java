package dev.linwood.api.request;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BlockBreakRequest extends Request<Block> {
    public BlockBreakRequest(final @NotNull Player player) {
        super(player);
    }
}
