package com.github.codedoctorde.api.ui.template.gui.pane.list;

import com.github.codedoctorde.api.ui.GuiPane;
import com.github.codedoctorde.api.ui.template.gui.ListGui;

import java.util.function.Function;

public class VerticalListControls extends ListControls {
    private VerticalAlignment alignment;
    public enum VerticalAlignment {
        left, right
    }
    public VerticalListControls(VerticalAlignment alignment) {
        this(alignment, true);
    }

    public VerticalListControls(VerticalAlignment alignment, boolean detailed) {
        super(detailed);
        this.alignment = alignment;
    }

    public VerticalListControls() {
        this(VerticalAlignment.right);
    }

    public VerticalListControls(boolean detailed) {
        this(VerticalAlignment.right, true);
    }

    public Function<ListGui, GuiPane> buildControlsBuilder() {
        return gui -> {
            var height = gui.getHeight();
            var width = gui.getWidth();
            var pane = new GuiPane(width, height);
            var x = VerticalAlignment.right == alignment ? width - 1 : 0;
            if (height < 3) return pane;
            pane.fillItems(VerticalAlignment.right == alignment ? (isDetailed() ? 7 : 8) : 0, 0, VerticalAlignment.right == alignment ? 8 : (isDetailed() ? 1 : 0), height - 1, getPlaceholderItem());
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
