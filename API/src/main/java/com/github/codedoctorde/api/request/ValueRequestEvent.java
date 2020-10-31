package com.github.codedoctorde.api.request;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public interface ValueRequestEvent extends RequestEvent<Float>  {
    default void onChange(final Player player, final Float value){
        player.sendTitle(null, String.valueOf(value), 20, 70, 10);
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);
    }
}
