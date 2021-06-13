package com.github.codedoctorde.api.ui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

/**
 * @author CodeDoctorDE
 */
public class GuiListener implements Listener {

    public boolean isRegistered() {
        return HandlerList.getRegisteredListeners(JavaPlugin.getProvidingPlugin(getClass())).stream().anyMatch(registeredListener -> registeredListener.getListener() instanceof GuiListener);
    }

    public static void register() {
        GuiListener instance = new GuiListener();
        if (instance.isRegistered())
            return;
        Bukkit.getPluginManager().registerEvents(instance,
                JavaPlugin.getProvidingPlugin(instance.getClass()));
    }

    @EventHandler
    public void onChestGuiItemClicked(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player))
            return;
        event.setCancelled(true);
        Gui gui = Gui.getGui((Player) event.getWhoClicked());
        int slot = event.getSlot();
        int x = slot % 9;
        int y = slot / 9;
        gui.getItem(x, y).onClick(event);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Gui.getGui(event.getPlayer()).hide(event.getPlayer());
    }

    @EventHandler
    public void onChestGuiClosed(InventoryCloseEvent event) {
        Gui.getGui((Player) event.getPlayer()).onClose((Player) event.getPlayer());
    }
}
