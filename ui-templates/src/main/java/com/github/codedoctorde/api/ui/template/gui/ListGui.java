package com.github.codedoctorde.api.ui.template.gui;

import com.github.codedoctorde.api.translations.Translation;
import com.github.codedoctorde.api.ui.ChestGui;
import com.github.codedoctorde.api.ui.GuiCollection;
import com.github.codedoctorde.api.ui.GuiPane;
import com.github.codedoctorde.api.ui.item.GuiItem;
import com.github.codedoctorde.api.ui.template.gui.pane.list.ListControls;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class ListGui extends GuiCollection {
    private Object[] placeholders = new Object[0];
    private final Function<ListGui, GuiItem[]> itemBuilder;
    private final int height;
    private final Translation translation;
    private Function<ListGui, GuiPane> controlsBuilder;
    private String searchText = "";
    private int controlsOffsetX = 0, controlsOffsetY = 0;

    public ListGui(Translation translation, int height, Function<ListGui, GuiItem[]> itemBuilder) {
        this.itemBuilder = itemBuilder;
        this.height = height;
        this.translation = translation;
        rebuild();
    }

    public ListGui(Translation translation, Function<ListGui, GuiItem[]> itemBuilder) {
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
        return placeholders;
    }

    public void setPlaceholders(Object... placeholders) {
        this.placeholders = placeholders;
        rebuild();
    }

    public void rebuild() {
        var openedPlayers = getOpenedPlayers();
        clearGuis();
        var width = getWidth();
        GuiItem[] items = itemBuilder.apply(this);
        GuiPane controls = controlsBuilder == null ? null : controlsBuilder.apply(this);
        int controlsCount = controls == null ? 0 : controls.getItemCount();
        int freeSlots = height * 9 - controlsCount;
        int pageCount = (int) Math.ceil(items.length / (float) freeSlots);
        if (pageCount <= 0)
            pageCount = 1;

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

    private TranslatedChestGui buildGui(int currentPage, int pageCount, GuiPane controls) {
        TranslatedChestGui gui = new TranslatedChestGui(translation, height);
        var placeholders = new ArrayList<>(Arrays.asList(this.placeholders));
        placeholders.add(currentPage);
        placeholders.add(pageCount);
        gui.setPlaceholders(placeholders.toArray(Object[]::new));
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
