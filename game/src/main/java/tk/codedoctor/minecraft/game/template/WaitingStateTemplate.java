package tk.codedoctor.minecraft.game.template;

import tk.codedoctor.minecraft.game.GameState;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import java.util.Objects;

/**
 * @author CodeDoctorDE
 */
public abstract class WaitingStateTemplate extends GameState {
    @EventHandler
    public void onPlayerHunger(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player))
            return;
        event.setCancelled(true);
        ((Player) event.getEntity()).setHealth(Objects.requireNonNull(((Player) event.getEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue());
    }
}
