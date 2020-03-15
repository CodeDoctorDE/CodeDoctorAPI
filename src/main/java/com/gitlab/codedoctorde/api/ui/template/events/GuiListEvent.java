package com.gitlab.codedoctorde.api.ui.template.events;

import com.gitlab.codedoctorde.api.ui.GuiItem;
import org.bukkit.inventory.ItemStack;

public interface GuiListEvent {
    String title(int index, int size);

    GuiItem[] pages(String output);

    String titleDelete(int index);

    GuiItem deleteYesItem(ItemStack itemStack, int index);

    GuiItem deleteNoItem(ItemStack itemStack, int index);
}
