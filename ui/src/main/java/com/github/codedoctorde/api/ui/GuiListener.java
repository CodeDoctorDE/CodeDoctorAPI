package com.github.codedoctorde.api.ui;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author CodeDoctorDE
 */
public class GuiListener implements Listener {
    private static boolean registered = false;
    public static boolean isRegistered(){
        return registered;
    }
    public static void register() {
        if(!isRegistered())
            return;
        GuiListener instance = new GuiListener();
        Bukkit.getPluginManager().registerEvents(instance,
                JavaPlugin.getProvidingPlugin(instance.getClass()));
        registered = true;
    }
}
