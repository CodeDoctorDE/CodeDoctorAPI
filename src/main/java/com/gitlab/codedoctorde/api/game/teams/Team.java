package com.gitlab.codedoctorde.api.game.teams;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CodeDoctorDE
 */
public class Team {
    private List<Player> players = new ArrayList<>();
    private String prefix = "";
    private String suffix = "";
    private String name;

    public Team(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void updateNames() {
        players.forEach(player -> player.setDisplayName(prefix + player.getName() + suffix));
    }
}
