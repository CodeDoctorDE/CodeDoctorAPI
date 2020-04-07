package com.gitlab.codedoctorde.api.game.teams;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CodeDoctorDE
 */
public class TeamManager {
    private List<Team> teams = new ArrayList<>();

    public TeamManager() {

    }

    public boolean assigned(Player player) {
        return teams.stream().anyMatch(team -> team.getPlayers().contains(player));
    }

    public void join(Player player, int index) {
        join(player, teams.get(index));
    }

    public void join(Player player, Team team) {
        leave(player);
        team.getPlayers().add(player);
    }

    public void leave(Player player) {
        teams.forEach(team -> team.getPlayers().remove(player));
    }

    @Nullable
    public Team currentTeam(Player player) {
        return teams.stream().filter(team -> team.getPlayers().contains(player)).findFirst().orElse(null);
    }

    public List<Team> getTeams() {
        return teams;
    }
}
