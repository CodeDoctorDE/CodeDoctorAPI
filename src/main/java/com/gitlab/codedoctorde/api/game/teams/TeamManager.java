package com.gitlab.codedoctorde.api.game.teams;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author CodeDoctorDE
 */
public class TeamManager<T extends Team> {
    private List<T> teams = new ArrayList<>();

    public TeamManager() {

    }

    public boolean assigned(Player player) {
        return teams.stream().anyMatch(team -> team.getPlayers().contains(player));
    }

    public boolean join(Player player, int index) {
        Team team = teams.get(index);
        if (team == null)
            return false;
        if (team.isFull())
            return false;
        join(player, teams.get(index));
        return true;
    }

    public boolean join(Player player, Team team) {
        if (team == null)
            return false;
        if (team.isFull())
            return false;
        leave(player);
        team.getPlayers().add(player);
        return true;
    }

    public boolean join(Player player, String name) {
        Team team = getTeam(name);
        if (team == null)
            return false;
        if (team.isFull())
            return false;
        leave(player);
        Objects.requireNonNull(team).getPlayers().add(player);
        return true;
    }

    public void leave(Player player) {
        teams.forEach(team -> team.getPlayers().remove(player));
    }

    @Nullable
    public Team currentTeam(Player player) {
        return teams.stream().filter(team -> team.getPlayers().contains(player)).findFirst().orElse(null);
    }

    @Nullable
    public Team getTeam(String name) {
        return teams.stream().filter(team -> team.getName().equals(name)).findFirst().orElse(null);
    }

    public List<T> getTeams() {
        return teams;
    }

    public boolean joinRandom(Player player) {
        leave(player);
        for (Team team :
                teams)
            if (!team.isFull()) {
                join(player, team);
                return true;
            }
        return false;
    }
}
