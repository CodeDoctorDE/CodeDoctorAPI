package com.github.codedoctorde.api.ui.template.gui.pane.list;

import com.github.codedoctorde.api.ui.GuiPane;
import com.github.codedoctorde.api.ui.template.gui.ListGui;

import java.util.function.Function;

public class VerticalListControls extends ListControls {
    public VerticalListControls() {
        super();
    }

    public VerticalListControls(boolean detailed) {
        super(true);
    }

    public Function<ListGui, GuiPane> buildControlsBuilder() {
        return gui -> {
            int height = gui.getHeight();
            var pane = new GuiPane(gui.getWidth(), height);
            var x = isDetailed() ? 1 : 0;
            if (height < 3) return pane;
            pane.fillItems(0, 0, isDetailed() ? 1 : 0, height, getPlaceholderItem());
            pane.registerItem(x, 0, getPreviousItem(gui));
            pane.registerItem(x, height / 2 - 1, getSearchItem(gui));
            pane.registerItem(x, height - 1, getNextItem(gui));
            if (createAction != null && height > 3)
                pane.registerItem(x, height / 2, getCreateItem(gui));

            if (height >= 5 && isDetailed()) {
                pane.registerItem(x, 1, getFirstItem(gui));
                pane.registerItem(x, height - 2, getLastItem(gui));
            }
            return pane;
        };
    }
}
