package com.github.codedoctorde.api.ui.template.gui;

import com.github.codedoctorde.api.translations.Translation;
import com.github.codedoctorde.api.ui.ChestGui;
import com.github.codedoctorde.api.ui.GuiCollection;
import com.github.codedoctorde.api.ui.GuiItem;
import com.github.codedoctorde.api.ui.GuiPane;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ListGui extends GuiCollection {
    private final List<Object> placeholders = new ArrayList<>();
    private final Function<String, GuiItem[]> itemBuilder;
    private final int size;
    private final Function<ListGui, GuiPane> controlsBuilder;
    private final Translation translation;
    private String searchText = "";

    public ListGui(Translation translation, int size, Function<ListGui, GuiPane> controlsBuilder, Function<String, GuiItem[]> itemBuilder){
        this.itemBuilder = itemBuilder;
        this.controlsBuilder = controlsBuilder;
        this.size = size;
        this.translation = translation;
        rebuild();
    }
    public ListGui(Translation translation, Function<ListGui, GuiPane> controlsBuilder, Function<String, GuiItem[]> itemBuilder){
        this(translation, 5, controlsBuilder, itemBuilder);
    }

    public void setPlaceholders(Object... placeholders){
        this.placeholders.clear();
        this.placeholders.addAll(Collections.singleton(placeholders));
    }

    public Object[] getPlaceholders() {
        return placeholders.toArray();
    }

    public void rebuild(){
        GuiItem[] items = itemBuilder.apply(searchText);
        GuiPane controls = controlsBuilder.apply(this);
        int controlsCount = controls.getItemCount();
        int freeSlots = size * 9 - controlsCount;
        int pageCount = (int) Math.ceil(items.length / (float) freeSlots);

        int currentPage = 1;

        ChestGui currentGui = buildGui(currentPage, pageCount, controls);
        clearGuis();
        registerGui(currentGui);

        for (int i = 0, pageItemCount = 0; i < items.length; i++) {
            if (size * 9 >= pageItemCount + controlsCount) {
                currentPage++;
                currentGui = buildGui(currentPage, pageCount, controls);
                registerGui(currentGui);
            }
            currentGui.addItem(items[i]);
            pageItemCount++;
        }
        show(getOpenedPlayers());
    }
    private ChestGui buildGui(int currentPage, int pageCount, GuiPane controls){
        ChestGui gui = new ChestGui(translation.getTranslation("title", currentPage, pageCount, placeholders));
        gui.addPane(controls);
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
}
