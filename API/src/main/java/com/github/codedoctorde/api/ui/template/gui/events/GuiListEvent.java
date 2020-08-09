package com.github.codedoctorde.api.ui.template.gui.events;

import com.github.codedoctorde.api.ui.GuiItem;

public interface GuiListEvent {
    String title(int index, int size);

    GuiItem[] pages(String output);
}
