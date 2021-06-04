package com.github.codedoctorde.api.ui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public abstract class Gui extends GuiPane {
    private static final Map<UUID, Gui> playerGuis = new HashMap<>();
    private Consumer<Player> openAction;
    private Consumer<Player> closeAction;

    public Gui(int width, int height) {
        super(width, height);
        GuiListener.register();
    }

    public static Gui getGui(@NotNull Player player) {
        return playerGuis.get(player.getUniqueId());
    }

    public static boolean hasGui(@NotNull Player player) {
        return playerGuis.containsKey(player.getUniqueId());
    }

    public static void hideAll(@NotNull Player... players) {
        Arrays.stream(players).filter(Gui::hasGui).forEach(player -> getGui(player).hide(player));
    }

    public void show(@NotNull Player... players) {
        for (Player player : players) {
            if (hasGui(player))
                getGui(player).hide();
            playerGuis.put(player.getUniqueId(), this);
            register(player);
        }
    }

    public void hide(@NotNull Player... players) {
        for (Player player : players) {
            if (playerGuis.containsKey(player.getUniqueId())) {
                playerGuis.remove(player.getUniqueId());
                unregister(player);
            }
        }
    }

    protected void register(@NotNull Player player) {

    }

    protected void unregister(@NotNull Player player) {

    }

    public void reloadAll() {
        reload(getOpenedPlayers());
    }

    public abstract void reload(@NotNull Player... players);

    public Player[] getOpenedPlayers() {
        return playerGuis.entrySet().stream().filter(uuidGuiEntry -> uuidGuiEntry.getValue().equals(this)).map(Map.Entry::getKey).map(Bukkit::getPlayer).toArray(Player[]::new);
    }

    public boolean hasCurrentGui(@NotNull final Player player) {
        if (!playerGuis.containsKey(player.getUniqueId()))
            return false;
        return playerGuis.get(player.getUniqueId()).equals(this);
    }

    public void setOpenAction(@Nullable Consumer<Player> openAction) {
        this.openAction = openAction;
    }

    public void setCloseAction(@Nullable Consumer<Player> closeAction) {
        this.closeAction = closeAction;
    }

    protected void onOpen(@NotNull Player player) {
        openAction.accept(player);
    }

    protected void onClose(@NotNull Player player) {
        closeAction.accept(player);
    }
}
