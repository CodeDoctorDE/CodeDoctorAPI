package com.github.codedoctorde.api.request;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * @author CodeDoctorDE
 */
public abstract class Request<T> implements Listener {
    protected final Player player;
    protected Consumer<T> submitAction = (t) -> { };
    protected Runnable cancelAction = () -> { };
    private static final HashMap<UUID, Request> requests = new HashMap<>();

    public Request(final Player player) {
        this.player = player;
        if(requests.containsKey(player.getUniqueId()))
            requests.get(player.getUniqueId()).cancel();
        requests.put(player.getUniqueId(), this);
    }

    public void raise(T output) {
        submitAction.accept(output);
        unregister();
    }

    public void unregister() {
        requests.remove(player.getUniqueId());
    }

    public void cancel() {
        cancelAction.run();
        unregister();
    }

    public Player getPlayer() {
        return player;
    }

    @EventHandler
    private void onPlayerInteract(PlayerInteractEvent event) {
        Player current = event.getPlayer();
        if (event.getAction() != Action.LEFT_CLICK_AIR)
            return;
        if(player.getUniqueId().equals(current.getUniqueId()))
            cancel();
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (requests.containsKey(event.getPlayer().getUniqueId()))
            requests.get(event.getPlayer().getUniqueId()).cancel();
    }

    public void setCancelAction(Runnable cancelAction) {
        this.cancelAction = cancelAction;
    }

    public void setSubmitAction(Consumer<T> submitAction) {
        this.submitAction = submitAction;
    }
    public static @Nullable Request getRequest(@NotNull Player player) {
        return requests.get(player.getUniqueId());
    }
    public static boolean hasRequest(@NotNull Player player){
        return requests.containsKey(player.getUniqueId());
    }

    public static void cancelAll(@NotNull Player... players){
        Arrays.stream(players).filter(Request::hasRequest).forEach(player -> {
            Request request = getRequest(player);
            if(request != null)
                request.cancel();
        });
    }
}
