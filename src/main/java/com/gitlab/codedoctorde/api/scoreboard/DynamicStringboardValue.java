package com.gitlab.codedoctorde.api.scoreboard;

import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.text.MessageFormat;
import java.util.UUID;

/**
 * @author CodeDoctorDE
 */
public class DynamicStringboardValue {
    private Team team = null;
    private String value;
    private String formattedValue;
    private int score = 0;
    private String entry;

    public DynamicStringboardValue(String value, Scoreboard scoreboard, String entry) {
        this(value, 0, scoreboard, entry);
    }

    public DynamicStringboardValue(String value, Stringboard stringboard, String entry) {
        this(value, stringboard.getScoreboard(), entry);
    }

    public DynamicStringboardValue(String value, int score, Scoreboard scoreboard, String entry) {
        this(value, score, scoreboard.registerNewTeam(UUID.randomUUID().toString()), entry);
    }

    public DynamicStringboardValue(String value, int score, Stringboard stringboard, String entry) {
        this(value, score, stringboard.getScoreboard().registerNewTeam(UUID.randomUUID().toString()), entry);
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

    public void recreateTeam(Stringboard stringboard) {
        recreateTeam(stringboard.getScoreboard());
    }

    public void recreateTeam(Scoreboard scoreboard) {
        String teamName = UUID.randomUUID().toString();
        while (scoreboard.getTeams().contains(teamName))
            teamName = UUID.randomUUID().toString();
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
