package com.gitlab.codedoctorde.api.scoreboard;

import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.UUID;

/**
 * @author CodeDoctorDE
 */
public class DynamicStringboardValue {
    private Team team = null;
    private String value;
    private int score = 0;
    private String entry;

    public DynamicStringboardValue(String value, Scoreboard scoreboard, String entry) {
        this.value = value;
        this.entry = entry;
        recreateTeam(scoreboard, entry);
        team.setPrefix(value);
    }

    public DynamicStringboardValue(String value, int score, Scoreboard scoreboard, String entry) {
        this.value = value;
        this.score = score;
        this.entry = entry;
        recreateTeam(scoreboard, entry);
        team.setPrefix(value);
    }

    public DynamicStringboardValue(String value, int score, Team team, String entry) {
        this.value = value;
        this.score = score;
        this.team = team;
        team.addEntry(entry);
        this.entry = entry;
        team.setPrefix(value);
    }

    public void recreateTeam(Scoreboard scoreboard, String entry) {
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
        updateValue();
    }

    public void updateValue() {
        team.setPrefix(value);
    }
}
