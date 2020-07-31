package com.gitlab.codedoctorde.api.ui.template.gui.events;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface ItemCreatorEvent {
    void onEvent(Player player, ItemStack itemStack);
    void onCancel(Player player);
}
