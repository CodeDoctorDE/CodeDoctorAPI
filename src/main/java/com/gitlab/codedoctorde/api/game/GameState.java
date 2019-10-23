package com.gitlab.codedoctorde.api.game;

import org.bukkit.event.Listener;

public abstract class GameState implements Listener {
    abstract void start();

    abstract void stop();
}
