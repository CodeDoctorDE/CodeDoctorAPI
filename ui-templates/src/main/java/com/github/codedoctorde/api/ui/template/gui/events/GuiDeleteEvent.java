package com.github.codedoctorde.api.ui.template.gui.events;

import com.github.codedoctorde.api.ui.StaticItem;
import com.github.codedoctorde.api.utils.ItemStackBuilder;

public interface GuiDeleteEvent {

    String title(int index);

    StaticItem yesItem(ItemStackBuilder itemStack, int index);

    StaticItem noItem(ItemStackBuilder itemStack, int index);
}
