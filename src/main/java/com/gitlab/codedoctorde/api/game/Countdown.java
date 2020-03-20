package com.gitlab.codedoctorde.api.game;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author CodeDoctorDE
 */
public abstract class Countdown {
    private final JavaPlugin plugin;
    protected int idleTime, countdownTime, time;
    protected Integer taskID;

    public Countdown(JavaPlugin plugin, int idleTime, int countdownTime) {
        this.plugin = plugin;
        this.idleTime = idleTime;
        this.countdownTime = countdownTime;
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

}
