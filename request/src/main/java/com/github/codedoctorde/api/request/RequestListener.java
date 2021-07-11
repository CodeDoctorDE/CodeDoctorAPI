package com.github.codedoctorde.api.request;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.*;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author CodeDoctorDE
 */
public class RequestListener implements Listener {
    private static boolean registered = false;

    public static void register() {
        RequestListener instance = new RequestListener();
        if (instance.isRegistered())
            return;
        Bukkit.getPluginManager().registerEvents(instance,
                JavaPlugin.getProvidingPlugin(instance.getClass()));
    }

    public boolean isRegistered() {
        return HandlerList.getRegisteredListeners(JavaPlugin.getProvidingPlugin(getClass())).stream().anyMatch(registeredListener -> registeredListener.getListener() instanceof RequestListener);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player current = event.getPlayer();
        Request<?> request = Request.getRequest(current);
        if (request instanceof ChatRequest) {
            ChatRequest itemRequest = (ChatRequest) request;
            event.setCancelled(true);
            Bukkit.getScheduler().runTask(JavaPlugin.getProvidingPlugin(getClass()), () -> itemRequest.raise(event.getMessage()));
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        Player current = event.getPlayer();
        Request<?> request = Request.getRequest(current);
        if (request instanceof ItemRequest) {
            ItemRequest itemRequest = (ItemRequest) request;
            itemRequest.raise(event.getItemDrop().getItemStack());
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player current = event.getPlayer();
        Request<?> request = Request.getRequest(current);
        if (request instanceof BlockBreakRequest) {
            BlockBreakRequest blockBreakRequest = (BlockBreakRequest) request;
            blockBreakRequest.raise(event.getBlock());
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onPlayerInteract(PlayerInteractEvent event) {
        Player current = event.getPlayer();
        if (event.getAction() != Action.LEFT_CLICK_AIR)
            return;
        Request<?> request = Request.getRequest(current);
        if (request instanceof ChatRequest) {
            request.cancel();
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player current = event.getPlayer();
        Request<?> request = Request.getRequest(current);
        if (request != null)
            request.cancel();
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player current = event.getPlayer();
        Request<?> request = Request.getRequest(current);
        if (request instanceof BlockPlaceRequest) {
            BlockPlaceRequest blockBreakRequest = (BlockPlaceRequest) request;
            blockBreakRequest.raise(event.getBlockPlaced());
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onValueChange(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        Request<?> request = Request.getRequest(player);
        if (request instanceof ValueRequest) {
            ValueRequest valueRequest = (ValueRequest) request;
            float current = (event.getPreviousSlot() > event.getNewSlot() ? event.getPreviousSlot() - event.getNewSlot() : event.getNewSlot() - event.getPreviousSlot()) * valueRequest.getSteps();
            if (event.getPlayer().isSneaking())
                current *= valueRequest.getFastSteps();
            valueRequest.setValue(current);
            event.setCancelled(true);
            player.sendTitle(null, String.valueOf(valueRequest.getValue()), 20, 70, 10);
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onValueSubmit(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Request<?> request = Request.getRequest(player);
        if (request instanceof ValueRequest) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
                ((ValueRequest) request).submit();
        }
    }
}
