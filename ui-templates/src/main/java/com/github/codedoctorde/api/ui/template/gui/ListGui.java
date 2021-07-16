package com.github.codedoctorde.api.ui.template.gui;

import com.github.codedoctorde.api.translations.Translation;
import com.github.codedoctorde.api.ui.ChestGui;
import com.github.codedoctorde.api.ui.GuiCollection;
import com.github.codedoctorde.api.ui.GuiPane;
import com.github.codedoctorde.api.ui.item.GuiItem;
import com.github.codedoctorde.api.ui.template.gui.pane.list.ListControls;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ListGui extends GuiCollection {
    private final List<Object> placeholders = new ArrayList<>();
    private final BiFunction<String, Translation, GuiItem[]> itemBuilder;
    private final int height;
    private final Translation translation;
    private Function<ListGui, GuiPane> controlsBuilder;
    private String searchText = "";
    private int controlsOffsetX = 0, controlsOffsetY = 0;

    public ListGui(Translation translation, int height, BiFunction<String, Translation, GuiItem[]> itemBuilder) {
        this.itemBuilder = itemBuilder;
        this.height = height;
        this.translation = translation;
        rebuild();
    }

    public ListGui(Translation translation, BiFunction<String, Translation, GuiItem[]> itemBuilder) {
        this(translation, 3, itemBuilder);
    }

    public void setControlsBuilder(Function<ListGui, GuiPane> controlsBuilder) {
        this.controlsBuilder = controlsBuilder;
        rebuild();
    }

    public void setListControls(ListControls controls) {
        setControlsBuilder(controls.buildControlsBuilder());
    }

    public Object[] getPlaceholders() {
        return placeholders.toArray();
    }

    public void setPlaceholders(Object... placeholders) {
        this.placeholders.clear();
        this.placeholders.addAll(Collections.singleton(placeholders));
        rebuild();
    }

    public void rebuild() {
        var openedPlayers = getOpenedPlayers();
        clearGuis();
        var width = getWidth();
        GuiItem[] items = itemBuilder.apply(searchText, translation);
        GuiPane controls = controlsBuilder == null ? null : controlsBuilder.apply(this);
        int controlsCount = controls == null ? 0 : controls.getItemCount();
        int freeSlots = height * 9 - controlsCount;
        int pageCount = (int) Math.ceil(items.length / (float) freeSlots);

        int currentPage = 1;

        ChestGui currentGui = buildGui(currentPage, pageCount, controls);
        registerGui(currentGui);

        for (int i = 0, pageItemCount = 0; i < items.length; i++) {
            if (height * width <= pageItemCount + controlsCount) {
                currentPage++;
                pageItemCount = 0;
                currentGui = buildGui(currentPage, pageCount, controls);
                registerGui(currentGui);
            }
            currentGui.addItem(items[i]);
            pageItemCount++;
        }
        toFirst();
        show(openedPlayers);
    }

    private ChestGui buildGui(int currentPage, int pageCount, GuiPane controls) {
        ChestGui gui = new ChestGui(translation.getTranslation("title", currentPage, pageCount, placeholders), height);
        if (controls != null)
            gui.addPane(controlsOffsetX, controlsOffsetY, controls);
        return gui;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
        rebuild();
    }

    public Translation getTranslation() {
        return translation;
    }

    public void controlsOffset(int x, int y) {
        controlsOffsetX = x;
        controlsOffsetY = y;
        rebuild();
    }
    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return 9;
    }
}
