package com.gitlab.codedoctorde.api.request;

import org.bukkit.entity.Player;

public abstract class ChatRequestEvent {
    public void onEvent(final Player player, final String output) {

    }

    public void onCancel(final Player player) {

    }
}
