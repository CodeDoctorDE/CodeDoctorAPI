package com.gitlab.codedoctorde.api.ui.template.events;

import com.gitlab.codedoctorde.api.ui.GuiItem;

public interface GuiListEvent {
    String title(int index);

    GuiItem[] pages(String output);
}
