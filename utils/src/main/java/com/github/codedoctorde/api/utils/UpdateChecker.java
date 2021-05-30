package com.github.codedoctorde.api.utils;

/**
 * @author CodeDoctorDE
 */

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Consumer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

/**
 * Source: https://www.spigotmc.org/wiki/creating-an-update-checker-that-checks-for-updates/
 */
public class UpdateChecker {

    private final Plugin plugin;
    private final int resourceId;

    public UpdateChecker(Plugin plugin, int resourceId) {
        this.plugin = plugin;
        this.resourceId = resourceId;
    }

    public void getVersion(final Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try (InputStream inputStream = new URL("https://api.spiget.org/v2/resources/" + this.resourceId + "/versions/latest").openStream(); Scanner scanner = new Scanner(inputStream)) {
                if (scanner.hasNext()) consumer.accept(new Gson().fromJson(scanner.next(), JsonObject.class).get("name").getAsString());
            } catch (IOException exception) {
                this.plugin.getLogger().info("Cannot look for updates: " + exception.getMessage());
            }
        });
    }
}
