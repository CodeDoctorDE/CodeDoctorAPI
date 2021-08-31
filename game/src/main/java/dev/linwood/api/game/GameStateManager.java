package dev.linwood.api.game;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * @author CodeDoctorDE
 */
public class GameStateManager {
    private final JavaPlugin plugin;
    private GameState currentGameState;

    public GameStateManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }

    public void setCurrentGameState(@NotNull GameState currentGameState) {
        if (this.currentGameState != null) {
            this.currentGameState.stop();
            HandlerList.unregisterAll(this.currentGameState);
        }
        this.currentGameState = currentGameState;
        Bukkit.getPluginManager().registerEvents(this.currentGameState, plugin);
        currentGameState.start();
    }
}
