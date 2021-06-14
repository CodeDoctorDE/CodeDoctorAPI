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
            var pane = new GuiPane(getWidth(), getHeight());
            pane.fillItems(0, 0, 8, isDetailed() ? 2 : 1, getPlaceholderItem());
            pane.registerItem(0, 0, getPreviousItem(gui));
            pane.registerItem(0, 4, getSearchItem(gui));
            pane.registerItem(0, 8, getNextItem(gui));
            if (createAction != null)
                pane.registerItem(0, 5, getCreateItem(gui));

            if (isDetailed()) {
                pane.registerItem(0, 1, getFirstItem(gui));
                pane.registerItem(8, 7, getLastItem(gui));
            }
            return pane;
        };
    }
}
