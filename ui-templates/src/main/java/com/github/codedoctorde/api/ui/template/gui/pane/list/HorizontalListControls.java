package com.github.codedoctorde.api.ui.template.gui.pane.list;

import com.github.codedoctorde.api.ui.GuiPane;
import com.github.codedoctorde.api.ui.template.gui.ListGui;

import java.util.function.Function;

public class HorizontalListControls extends ListControls {

    public HorizontalListControls() {
        super();
    }

    public HorizontalListControls(boolean detailed) {
        super(detailed);
    }

    public Function<ListGui, GuiPane> buildControlsBuilder() {
        return gui -> {
            var pane = new GuiPane(gui.getWidth(), gui.getHeight());
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
