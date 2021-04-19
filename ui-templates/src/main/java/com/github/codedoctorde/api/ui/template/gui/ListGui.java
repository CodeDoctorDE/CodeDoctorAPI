package com.github.codedoctorde.api.ui.template.gui;

import com.github.codedoctorde.api.translations.Translation;
import com.github.codedoctorde.api.ui.ChestGui;
import com.github.codedoctorde.api.ui.GuiCollection;
import com.github.codedoctorde.api.ui.GuiItem;
import com.github.codedoctorde.api.ui.GuiPane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class ListGui extends GuiCollection {
    private final List<Object> placeholders = new ArrayList<>();
    private final Function<String, GuiItem[]> itemBuilder;
    private final int size;
    private final GuiPane controls;
    private final Translation translation;

    public ListGui(Translation translation, int size, GuiPane controls, Function<String, GuiItem[]> itemBuilder){
        this.itemBuilder = itemBuilder;
        this.controls = controls;
        this.size = size;
        this.translation = translation;
        rebuild();
    }
    public ListGui(Translation translation, GuiPane controls, Function<String, GuiItem[]> itemBuilder){
        this(translation, 5, controls, itemBuilder);
    }

    public void setPlaceholders(Object... placeholders){
        this.placeholders.clear();
        this.placeholders.addAll(Collections.singleton(placeholders));
    }

    public Object[] getPlaceholders() {
        return placeholders.toArray();
    }

    public void rebuild(){
        rebuild("");
    }
    public void rebuild(String searchText){
        GuiItem[] items = itemBuilder.apply(searchText);
        int controlsCount = controls.getItemCount();
        int freeSlots = size * 9 - controlsCount;
        int pageCount = (int) Math.ceil(items.length / (float) freeSlots);

        int currentPage = 1;

        ChestGui currentGui = buildGui(currentPage, pageCount);
        clearGuis();
        registerGui(currentGui);

        for (int i = 0, pageItemCount = 0; i < items.length; i++) {
            if(size * 9 >= pageItemCount + controlsCount) {
                currentPage++;
                currentGui = buildGui(currentPage, pageCount);
                registerGui(currentGui);
            }
            currentGui.addItem(items[i]);
            pageItemCount++;
        }
    }
    private ChestGui buildGui(int currentPage, int pageCount){
        ChestGui gui = new ChestGui(translation.getTranslation("title", currentPage, pageCount, placeholders));
        gui.addPane(controls);
        return gui;
    }
}
