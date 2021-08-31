package dev.linwood.api.game.teams;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CodeDoctorDE
 */
public class Team {
    private final List<Player> players = new ArrayList<>();
    private String prefix = "";
    private String suffix = "";
    private String name;
    private int max;

    public Team(String name, int max) {
        this.name = name;
        this.max = max;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
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

    public @NotNull List<Player> getPlayers() {
        return players;
    }

    public void updateNames() {
        players.forEach(player -> player.setDisplayName(prefix + player.getName() + suffix));
    }

    public boolean isFull() {
        return players.size() >= max;
    }
}
