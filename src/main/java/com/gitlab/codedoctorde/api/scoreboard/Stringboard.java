package com.gitlab.codedoctorde.api.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author CodeDoctorDE
 */
public class Stringboard {
    private final Objective objective;
    private List<StringboardValue> values = new ArrayList<>();
    private List<DynamicStringboardValue> dynamicValues = new ArrayList<>();
    private String title;

    public Stringboard(Scoreboard scoreboard, String title) {
        this(scoreboard.registerNewObjective(UUID.randomUUID().toString(), "dummy", title));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public Stringboard(String title) {
        this(Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard().registerNewObjective(UUID.randomUUID().toString(), "dummy", title));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public Stringboard(Objective objective) {
        this.objective = objective;
    }

    public void rebuild() {
        if (objective.getScoreboard() != null)
            objective.getScoreboard().getEntries().clear();
        objective.setDisplayName(title);
        objective.getScoreboard().getEntries().forEach(entry -> objective.getScoreboard().resetScores(entry));
        values.forEach(value -> objective.getScore(value.getValue()).setScore(value.getScore()));
        dynamicValues.forEach(value -> objective.getScore(value.getEntry()).setScore(value.getScore()));
    }

    public List<StringboardValue> getValues() {
        return values;
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

    public Scoreboard getScoreboard() {
        return objective.getScoreboard();
    }

    public List<DynamicStringboardValue> getDynamicValues() {
        return dynamicValues;
    }

    public void updateTitle() {
        objective.setDisplayName(title);
    }

    public void updateTitle(String title) {
        this.title = title;
        updateTitle();
    }
}
