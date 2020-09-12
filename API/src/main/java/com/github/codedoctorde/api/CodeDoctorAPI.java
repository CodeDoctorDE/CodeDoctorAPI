package com.github.codedoctorde.api;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author CodeDoctorDE
 */
public class CodeDoctorAPI {
    private final JavaPlugin plugin;

    public CodeDoctorAPI(final JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public String getVersion(){
        return getClass().getPackage().getImplementationVersion();
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }
}
