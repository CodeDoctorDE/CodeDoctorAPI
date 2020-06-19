package com.gitlab.codedoctorde.api.scoreboard;

import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.text.MessageFormat;
import java.util.Objects;

/**
 * @author CodeDoctorDE
 */
public class DynamicStringboardValue {
    private Team team = null;
    private String value;
    private String formattedValue;
    private int score = 0;
    private final String entry;

    public DynamicStringboardValue(String value, Scoreboard scoreboard, String entry, String teamName) {
        this(value, 0, scoreboard, entry, teamName);
    }

    public DynamicStringboardValue(String value, Stringboard stringboard, String entry, String teamName) {
        this(value, stringboard.getScoreboard(), entry, teamName);
    }

    public DynamicStringboardValue(String value, int score, Scoreboard scoreboard, String entry, String teamName) {
        this(value, score, scoreboard.getTeam(teamName) != null ? Objects.requireNonNull(scoreboard.getTeam(teamName)) : scoreboard.registerNewTeam(teamName), entry);
    }

    public DynamicStringboardValue(String value, int score, Stringboard stringboard, String entry, String teamName) {
        this(value, score, stringboard.getScoreboard(), entry, teamName);
    }

    public DynamicStringboardValue(String value, int score, Team team, String entry) {
        this.value = value;
        this.score = score;
        this.team = team;
        team.addEntry(entry);
        this.entry = entry;
        this.formattedValue = value;
        team.setPrefix(value);
    }

    public void recreateTeam(Stringboard stringboard, String teamName) {
        recreateTeam(stringboard.getScoreboard(), teamName);
    }

    public void recreateTeam(Scoreboard scoreboard, String teamName) {
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

    public Team getDynamic() {
        return team;
    }

    public String getEntry() {
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
