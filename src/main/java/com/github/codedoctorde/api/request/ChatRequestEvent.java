package com.github.codedoctorde.api.request;

import org.bukkit.entity.Player;

public interface ChatRequestEvent extends RequestEvent {
    void onEvent(final Player player, final String output);
}
