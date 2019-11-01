package com.gitlab.codedoctorde.api.utils;

import com.gitlab.codedoctorde.api.config.JsonConfigurationSection;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class ConfigUtils {
    public static JsonConfigurationSection locationToSection(Location location){
        JsonConfigurationSection config = new JsonConfigurationSection();
        config.setValue(location.getX(), "x");
        config.setValue(location.getY(), "y");
        config.setValue(location.getZ(), "z");
        config.setValue(location.getYaw(), "yaw");
        config.setValue(location.getPitch(), "pitch");
        config.setValue(location.getWorld().getName(), "world");
        return config;
    }
    public static Location sectionToLocation(JsonConfigurationSection section){
        return new Location(Bukkit.getWorld(section.getString("world")),
                section.getDouble("x"),
                section.getDouble("y"),
                section.getDouble("z"),
                section.getFloat("yaw"),
                section.getFloat("pitch"));
    }
}
