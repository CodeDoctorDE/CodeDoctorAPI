package com.github.codedoctorde.api.ui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author CodeDoctorDE
 */
public class GuiListener implements Listener {
    private static boolean registered = false;

    public static boolean isRegistered() {
        return registered;
    }

    public static void register() {
        if (isRegistered())
            return;
        GuiListener instance = new GuiListener();
        Bukkit.getPluginManager().registerEvents(instance,
                JavaPlugin.getProvidingPlugin(instance.getClass()));
        registered = true;
    }

    @EventHandler
    public void onChestGuiItemClicked(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player))
            return;
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
