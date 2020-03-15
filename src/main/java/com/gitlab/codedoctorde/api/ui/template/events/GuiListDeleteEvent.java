package com.gitlab.codedoctorde.api.ui.template.events;

import com.gitlab.codedoctorde.api.ui.GuiItem;
import org.bukkit.inventory.ItemStack;

public interface GuiListDeleteEvent {

    String title(int index);

    GuiItem yesItem(ItemStack itemStack, int index);

    GuiItem noItem(ItemStack itemStack, int index);
}
