package tk.codedoctor.minecraft.game;

import org.bukkit.event.Listener;

public abstract class GameState implements Listener {
    public abstract void start();

    public abstract void stop();
}
