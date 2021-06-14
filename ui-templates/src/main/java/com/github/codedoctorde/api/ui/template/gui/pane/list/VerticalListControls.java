package com.github.codedoctorde.api.ui.template.gui.pane.list;

import com.github.codedoctorde.api.ui.GuiPane;
import com.github.codedoctorde.api.ui.template.gui.ListGui;

import java.util.function.Function;

public class VerticalListControls extends ListControls {
    public VerticalListControls() {
        this(3);
    }

    public VerticalListControls(int height) {
        this(true, height);
    }

    public VerticalListControls(boolean detailed, int height) {
        super(detailed, detailed ? 2 : 1, height);
    }

    public Function<ListGui, GuiPane> buildControlsBuilder() {
        return gui -> {
            int height = getHeight();
            var pane = new GuiPane(getWidth(), height);
            if (height < 3) return pane;
            pane.fillItems(0, 0, isDetailed() ? 1 : 0, height, getPlaceholderItem());
            pane.registerItem(1, 0, getPreviousItem(gui));
            pane.registerItem(1, height / 2, getSearchItem(gui));
            pane.registerItem(1, height - 1, getNextItem(gui));
            if (createAction != null && height > 3)
                pane.registerItem(1, height / 2, getCreateItem(gui));

            if (height >= 5 && isDetailed()) {
                pane.registerItem(1, 1, getFirstItem(gui));
                pane.registerItem(1, height - 2, getLastItem(gui));
            }
            return pane;
        };
    }
}
