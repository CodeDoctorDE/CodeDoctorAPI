package com.github.codedoctorde.api.request;

import org.bukkit.entity.Player;

public interface RequestEvent<T> {
    void onEvent(final Player player, final T output);
    void onCancel(final Player player);
}
