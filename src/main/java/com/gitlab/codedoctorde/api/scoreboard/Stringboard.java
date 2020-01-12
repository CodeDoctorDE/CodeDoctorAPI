package com.gitlab.codedoctorde.api.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.IntStream;

/**
 * @author CodeDoctorDE
 */
public class Stringboard {
    private final Objective objective;
    private String[] strings;
    private String title;

    public Stringboard(Scoreboard scoreboard, String title) {
        this(scoreboard.registerNewObjective(UUID.randomUUID().toString(), "dummy", title));
    }

    public Stringboard(String title) {
        this(Bukkit.getScoreboardManager().getNewScoreboard().registerNewObjective(UUID.randomUUID().toString(), "dummy", title));
    }

    public Stringboard(Objective objective) {
        this.objective = objective;
    }

    public void reload() {
        if (objective.getScoreboard() != null)
            objective.getScoreboard().getEntries().clear();
        objective.setDisplayName(title);
        IntStream.range(0, strings.length).forEach(i -> objective.getScore(strings[i]).setScore(strings.length - i));
    }

    public String[] getStrings() {
        return strings;
    }

    public void setStrings(String[] strings) {
        this.strings = strings;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void show(Player player) {
        player.setScoreboard(Objects.requireNonNull(objective.getScoreboard()));
    }

    public Scoreboard getScorebaord() {
        return objective.getScoreboard();
    }
}
