package com.github.codedoctorde.api.ui.template.gui.pane.list;

import com.github.codedoctorde.api.ui.GuiPane;
import com.github.codedoctorde.api.ui.template.gui.ListGui;

import java.util.function.Function;

public class HorizontalListControls extends ListControls {

    public HorizontalListControls() {
        this(true);
    }
    public HorizontalListControls(boolean detailed) {
        super(9, detailed ? 2 : 1);
    }
    public Function<ListGui, GuiPane> buildControlsBuilder() {
        return gui -> {
            fillItems(0, 0, 8, isDetailed() ? 2 : 1, getPlaceholderItem());
            registerItem(0, 0, getPreviousItem(gui));
            registerItem(0, 4, getSearchItem(gui));
            registerItem(0, 8, getNextItem(gui));
            if(createAction != null)
                registerItem(0, 5, getCreateItem(gui));

            if(isDetailed()) {
                registerItem(0, 1, getFirstItem(gui));
                registerItem(8, 7, getLastItem(gui));
            }
            return this;
        };
    }
}
