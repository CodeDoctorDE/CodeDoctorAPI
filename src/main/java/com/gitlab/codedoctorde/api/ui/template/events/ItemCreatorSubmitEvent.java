package com.gitlab.codedoctorde.api.ui.template.events;

import org.bukkit.inventory.ItemStack;

public interface ItemCreatorSubmitEvent {
    default void onEvent(ItemStack itemStack) {

    }
}
