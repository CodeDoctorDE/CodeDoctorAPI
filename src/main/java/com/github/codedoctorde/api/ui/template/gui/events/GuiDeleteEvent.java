package com.github.codedoctorde.api.ui.template.gui.events;

import com.github.codedoctorde.api.ui.GuiItem;
import com.github.codedoctorde.api.utils.ItemStackBuilder;

public interface GuiDeleteEvent {

    String title(int index);

    GuiItem yesItem(ItemStackBuilder itemStack, int index);

    GuiItem noItem(ItemStackBuilder itemStack, int index);
}
