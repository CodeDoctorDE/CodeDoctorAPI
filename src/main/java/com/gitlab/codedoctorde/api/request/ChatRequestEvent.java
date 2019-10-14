package com.gitlab.codedoctorde.api.request;

import org.bukkit.entity.Player;

public abstract class ChatRequestEvent {
    public abstract void onEvent(final Player player, final String output);

    public abstract void onCancel(final Player player);
}
