package com.github.codedoctorde.api.ui;

import com.github.codedoctorde.api.ui.item.GuiItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author CodeDoctorDE
 */
public class GuiListener implements Listener {

    public static void register() {
        GuiListener instance = new GuiListener();
        if (instance.isRegistered())
            return;
        Bukkit.getPluginManager().registerEvents(instance,
                JavaPlugin.getProvidingPlugin(instance.getClass()));
    }

    public boolean isRegistered() {
        return HandlerList.getRegisteredListeners(JavaPlugin.getProvidingPlugin(getClass())).stream().anyMatch(registeredListener -> registeredListener.getListener() instanceof GuiListener);
    }

    @EventHandler
    public void onChestGuiItemClicked(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player))
            return;
        Gui gui = Gui.getGui((Player) event.getWhoClicked());
        if (gui == null)
            return;
        event.setCancelled(true);
        int slot = event.getSlot();
        int x = slot % 9;
        int y = slot / 9;
        GuiItem item = gui.getItem(x, y);
        if (item != null)
            item.onClick(event);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        var gui = Gui.getGui(event.getPlayer());
        if (gui != null) gui.hide(event.getPlayer());
    }

    @EventHandler
    public void onChestGuiClosed(InventoryCloseEvent event) {
        var p = (Player) event.getPlayer();
        var gui = Gui.getGui(p);
        if (gui != null && event.getPlayer().getOpenInventory().getType() == InventoryType.CRAFTING) {
            Gui.playerGuis.remove(p.getUniqueId());
            gui.unregister(p);
        }
    }
}
