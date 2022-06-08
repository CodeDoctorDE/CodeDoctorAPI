package dev.linwood.api.gui.template.gui.pane.list;

import dev.linwood.api.gui.GuiPane;
import dev.linwood.api.gui.template.gui.ListGui;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class VerticalListControls extends ListControls {
    private final VerticalAlignment alignment;

    public VerticalListControls(VerticalAlignment alignment) {
        this(alignment, true);
    }

    public VerticalListControls(VerticalAlignment alignment, boolean detailed) {
        super(detailed);
        this.alignment = alignment;
    }

    public VerticalListControls() {
        this(VerticalAlignment.RIGHT);
    }

    public VerticalListControls(boolean detailed) {
        this(VerticalAlignment.RIGHT, true);
    }

    public @NotNull Function<ListGui, GuiPane> buildControlsBuilder() {
        return gui -> {
            var height = gui.getHeight();
            var width = gui.getWidth();
            var pane = new GuiPane(width, height);
            var x = VerticalAlignment.RIGHT == alignment ? width - 1 : 0;
            if (height < 3) return pane;
            pane.fillItems(VerticalAlignment.RIGHT == alignment ? (isDetailed() ? 7 : 8) : 0, 0, VerticalAlignment.RIGHT == alignment ? 8 : (isDetailed() ? 1 : 0), height - 1, getPlaceholderItem());
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

    public enum VerticalAlignment {
        LEFT, RIGHT
    }
}
