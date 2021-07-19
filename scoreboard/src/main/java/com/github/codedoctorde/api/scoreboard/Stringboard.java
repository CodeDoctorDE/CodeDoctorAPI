package com.github.codedoctorde.api.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author CodeDoctorDE
 */
public class Stringboard {
    private final @NotNull Objective objective;
    private final List<StringboardValue> values = new ArrayList<>();
    private final List<DynamicStringboardValue> dynamicValues = new ArrayList<>();
    private String title;

    public Stringboard(@NotNull Scoreboard scoreboard, @NotNull String title, @NotNull String name) {
        this(scoreboard.registerNewObjective(name, "dummy", title));
    }

    public Stringboard(@NotNull String title, @NotNull String name) {
        this(Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard().registerNewObjective(name, "dummy", title));
    }

    public Stringboard(@NotNull Objective objective) {
        this.title = objective.getDisplayName();
        this.objective = objective;
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        rebuild();
    }

    public void rebuild() {
        if (objective.getScoreboard() == null)
            return;
        objective.setDisplayName(title);
        objective.getScoreboard().getEntries().forEach(entry -> objective.getScoreboard().resetScores(entry));
        values.forEach(value -> objective.getScore(value.getValue()).setScore(value.getScore()));
        dynamicValues.forEach(value -> objective.getScore(value.getEntry()).setScore(value.getScore()));
    }

    public @NotNull List<StringboardValue> getValues() {
        return values;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void show(@NotNull Player player) {
        player.setScoreboard(Objects.requireNonNull(objective.getScoreboard()));
    }

    public @Nullable Scoreboard getScoreboard() {
        return objective.getScoreboard();
    }

    public @NotNull List<DynamicStringboardValue> getDynamicValues() {
        return dynamicValues;
    }

    public void updateTitle() {
        objective.setDisplayName(title);
    }

    public void updateTitle(String title) {
        this.title = title;
        updateTitle();
    }

    public void openScoreboard(Player @NotNull ... players) {
        Arrays.stream(players).forEach(player -> player.setScoreboard(Objects.requireNonNull(objective.getScoreboard())));
    }

    public void closeScoreboard(Player @NotNull ... players) {
        Arrays.stream(players).filter(player -> player.getScoreboard().equals(objective.getScoreboard())).forEach(player -> player.setScoreboard(Objects.requireNonNull(Bukkit.getServer().getScoreboardManager()).getNewScoreboard()));
    }
}
