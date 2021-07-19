package com.github.codedoctorde.api.utils;

/**
 * @author CodeDoctorDE
 */

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Consumer;
import org.jetbrains.annotations.NotNull;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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

    public void getVersion(final @NotNull Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try {
                StringBuilder result = new StringBuilder();
                URL url = new URL("https://api.spiget.org/v2/resources/" + this.resourceId + "/versions/latest");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                consumer.accept(new Gson().fromJson(new InputStreamReader(conn.getInputStream()), JsonObject.class).get("name").getAsString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
