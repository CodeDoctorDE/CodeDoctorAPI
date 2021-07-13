package com.github.codedoctorde.api.game;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author CodeDoctorDE
 */
public abstract class Countdown {
    private final JavaPlugin plugin;
    protected int idleTime = 500, countdownTime = 30, time;
    protected Integer taskID = null;

    public Countdown(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void startCountdown() {
        stopTask();
        time = countdownTime;
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                countdown();
                time--;
            }
        }, 0, 20);
    }

    public void startIdle() {
        stopTask();
        time = idleTime;
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                idle();
            }
        }, 0, idleTime);
    }

    public void stopTask() {
        if (taskID != null)
            Bukkit.getScheduler().cancelTask(taskID);
        taskID = null;
    }

    public abstract void countdown();

    public abstract void idle();

    public boolean isRunning() {
        return taskID != null;
    }
}
