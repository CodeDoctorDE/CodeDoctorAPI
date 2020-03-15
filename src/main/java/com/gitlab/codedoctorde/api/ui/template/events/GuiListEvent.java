package com.gitlab.codedoctorde.api.ui.template.events;

import com.gitlab.codedoctorde.api.ui.GuiItem;

public interface GuiListEvent {
    String title(int index, int size);

    GuiItem[] pages(String output);

    void delete(int index);

    String titleDelete(int index);

}
