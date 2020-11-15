package com.github.codedoctorde.api.request;

import com.github.codedoctorde.api.ui.Gui;
import com.github.codedoctorde.api.ui.GuiItem;
import com.github.codedoctorde.api.ui.GuiItemEvent;
import com.github.codedoctorde.api.ui.template.item.ValueItem;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author CodeDoctorDE
 */
public class ValueRequest extends Request<Float, PlayerInteractEvent> {
    private final float defaultValue;
    private float fastSkip = 5;
    private float skip = 1;
    private float value;

    public ValueRequest(final JavaPlugin plugin, final Player player, float value, float defaultValue, ValueRequestEvent valueRequestEvent) {
        super(plugin, player, valueRequestEvent);
        this.value = value;
        this.defaultValue = defaultValue;
    }

    public ValueRequest setSkip(float skip) {
        this.skip = skip;
        return this;
    }

    public float getSkip() {
        return skip;
    }

    public ValueRequest setFastSkip(float fastSkip) {
        this.fastSkip = fastSkip;
        return this;
    }

    public float getFastSkip() {
        return fastSkip;
    }

    @EventHandler
    public void onChange(PlayerItemHeldEvent event){
        if(!event.getPlayer().getUniqueId().equals(player.getUniqueId()))
            return;
        float current = (event.getPreviousSlot() > event.getNewSlot() ? event.getPreviousSlot() - event.getNewSlot() : event.getNewSlot() - event.getPreviousSlot()) * skip;
        if(event.getPlayer().isSneaking())
            current *= fastSkip;
        value = current;
        event.setCancelled(true);
        ((ValueRequestEvent)requestEvent).onChange(player, value);
    }
    @EventHandler
    public void onDrop(PlayerDropItemEvent event){
        if(!event.getPlayer().getUniqueId().equals(player.getUniqueId()))
            return;
        value = defaultValue;
        event.setCancelled(true);
    }

    @Override
    @EventHandler
    public void onEvent(PlayerInteractEvent event) {
        if(!event.getPlayer().getUniqueId().equals(player.getUniqueId()))
            return;
        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
            raise(value);
    }
}
