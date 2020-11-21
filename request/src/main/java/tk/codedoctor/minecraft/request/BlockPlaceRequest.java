package tk.codedoctor.minecraft.request;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author CodeDoctorDE
 */
public class BlockPlaceRequest extends Request<Block, BlockPlaceEvent> {
    public BlockPlaceRequest(final JavaPlugin plugin, final Player player, final RequestEvent<Block> blockPlaceRequestEvent) {
        super(plugin, player, blockPlaceRequestEvent);
    }

    @EventHandler
    public void onEvent(BlockPlaceEvent event) {
        Player current = event.getPlayer();
        if(!player.getUniqueId().equals(current.getUniqueId()))
            return;
        raise(event.getBlockPlaced());
        event.setCancelled(true);
    }
}
