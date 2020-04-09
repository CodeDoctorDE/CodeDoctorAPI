package com.gitlab.codedoctorde.api.game.template;

import com.gitlab.codedoctorde.api.game.GameState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

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
        event.setCancelled(event.getEntity() instanceof Player);
    }
}
