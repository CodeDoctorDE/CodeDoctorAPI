package dev.linwood.api.scoreboard;

import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;
import java.util.Objects;

/**
 * @author CodeDoctorDE
 */
public class DynamicStringboardValue {
    private final @NotNull String entry;
    private @Nullable Team team = null;
    private String value;
    private String formattedValue;
    private int score = 0;

    public DynamicStringboardValue(@NotNull String value, @NotNull Scoreboard scoreboard, @NotNull String entry, @NotNull String teamName) {
        this(value, 0, scoreboard, entry, teamName);
    }

    public DynamicStringboardValue(@NotNull String value, @NotNull Stringboard stringboard, @NotNull String entry, @NotNull String teamName) {
        this(value, stringboard.getScoreboard(), entry, teamName);
    }

    public DynamicStringboardValue(@NotNull String value, int score, @NotNull Scoreboard scoreboard, @NotNull String entry, @NotNull String teamName) {
        this(value, score, scoreboard.getTeam(teamName) != null ? Objects.requireNonNull(scoreboard.getTeam(teamName)) : scoreboard.registerNewTeam(teamName), entry);
    }

    public DynamicStringboardValue(@NotNull String value, int score, @NotNull Stringboard stringboard, @NotNull String entry, @NotNull String teamName) {
        this(value, score, stringboard.getScoreboard(), entry, teamName);
    }

    public DynamicStringboardValue(@NotNull String value, int score, @NotNull Team team, @NotNull String entry) {
        this.value = value;
        this.score = score;
        this.team = team;
        team.addEntry(entry);
        this.entry = entry;
        this.formattedValue = value;
        team.setPrefix(value);
    }

    public void recreateTeam(@NotNull Stringboard stringboard, @NotNull String teamName) {
        recreateTeam(stringboard.getScoreboard(), teamName);
    }

    public void recreateTeam(@NotNull Scoreboard scoreboard, @NotNull String teamName) {
        if (scoreboard.getTeam(teamName) != null)
            scoreboard.getTeams().remove(scoreboard.getTeam(teamName));
        this.team = scoreboard.registerNewTeam(teamName);
        this.team.addEntry(entry);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean getTeam() {
        return team != null;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public @Nullable Team getDynamic() {
        return team;
    }

    public @NotNull String getEntry() {
        return entry;
    }

    public void updateValue(String display) {
        this.value = value;
        this.formattedValue = value;
        updateValue();
    }

    public void format(Object... arguments) {
        this.formattedValue = MessageFormat.format(value, arguments);
        updateValue();
    }


    public void updateValue() {
        team.setPrefix(formattedValue);
    }
}
