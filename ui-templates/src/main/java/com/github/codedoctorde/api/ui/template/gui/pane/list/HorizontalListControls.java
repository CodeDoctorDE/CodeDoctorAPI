package com.github.codedoctorde.api.ui.template.gui.pane.list;

import com.github.codedoctorde.api.ui.GuiPane;
import com.github.codedoctorde.api.ui.template.gui.ListGui;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class HorizontalListControls extends ListControls {
    private HorizontalAlignment alignment;

    public HorizontalListControls() {
        super();
    }

    public HorizontalListControls(boolean detailed) {
        super(detailed);
    }

    public @NotNull Function<ListGui, GuiPane> buildControlsBuilder() {
        return gui -> {
            var height = gui.getHeight();
            var pane = new GuiPane(gui.getWidth(), height);
            var y = HorizontalAlignment.top == alignment ? 0 : height - 1;
            pane.fillItems(0, HorizontalAlignment.bottom == alignment ? (isDetailed() ? height - 2 : height - 1) : 0, 8, HorizontalAlignment.top == alignment ? (isDetailed() ? 2 : 1) : height - 1, getPlaceholderItem());
            pane.registerItem(0, y, getPreviousItem(gui));
            pane.registerItem(4, y, getSearchItem(gui));
            pane.registerItem(8, y, getNextItem(gui));
            if (createAction != null)
                pane.registerItem(5, y, getCreateItem(gui));

            if (isDetailed()) {
                pane.registerItem(1, y, getFirstItem(gui));
                pane.registerItem(7, y, getLastItem(gui));
            }
            return pane;
        };
    }

    public enum HorizontalAlignment {
        top, bottom
    }
}
