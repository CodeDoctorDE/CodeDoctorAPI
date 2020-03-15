package com.gitlab.codedoctorde.api.ui.template.events;

import com.gitlab.codedoctorde.api.ui.GuiItem;
import com.gitlab.codedoctorde.api.utils.ItemStackBuilder;

public interface GuiDeleteEvent {

    String title(int index);

    GuiItem yesItem(ItemStackBuilder itemStack, int index);

    GuiItem noItem(ItemStackBuilder itemStack, int index);
}
