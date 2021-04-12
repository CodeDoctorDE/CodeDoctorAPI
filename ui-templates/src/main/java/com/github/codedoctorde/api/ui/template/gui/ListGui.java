package com.github.codedoctorde.api.ui.template.gui;

import com.github.codedoctorde.api.ui.GuiEvent;
import com.github.codedoctorde.api.ui.ChestGui;
import com.github.codedoctorde.api.ui.StaticItem;
import com.github.codedoctorde.api.ui.template.gui.events.GuiListEvent;
import com.google.gson.JsonObject;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class ListGui {
    private final GuiItemEvent createEvent;
    private final GuiListEvent listEvent;
    private final JavaPlugin plugin;
    private final GuiEvent guiEvent;
    private final boolean search;
    private final JsonObject guiTranslation;
    private int size = 5;

    public ListGui(JsonObject guiTranslation, JavaPlugin plugin, GuiItemEvent createEvent, GuiListEvent listEvent, GuiEvent guiEvent, boolean search) {
        this.plugin = plugin;
        this.listEvent = listEvent;
        this.guiEvent = guiEvent;
        this.createEvent = createEvent;
        this.search = search;
        this.guiTranslation = guiTranslation;
    }

    public ListGui(JsonObject guiTranslation, JavaPlugin plugin, GuiListEvent listEvent, GuiEvent guiEvent, boolean search) {
        this(guiTranslation, plugin, null, listEvent, guiEvent, search);
    }

    public ListGui(JsonObject guiTranslation, JavaPlugin plugin, GuiItemEvent createEvent, GuiListEvent listEvent, GuiEvent guiEvent) {
        this(guiTranslation, plugin, createEvent, listEvent, guiEvent, true);
    }

    public ListGui(JsonObject guiTranslation, JavaPlugin plugin, GuiListEvent listEvent, GuiEvent guiEvent) {
        this(guiTranslation, plugin, null, listEvent, guiEvent, true);
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public ChestGui[] createGuis() {
        return createGuis(null, "");
    }

    public ChestGui[] createGuis(String searchText) {
        return createGuis(null, searchText);
    }

    public ChestGui[] createGuis(ChestGui backGui) {
        return createGuis(backGui, "");
    }

    public ChestGui[] createGuis(ChestGui backGui, String searchText) {
        List<ChestGui> guiPages = new ArrayList<>();
        StaticItem[] items = listEvent.pages(searchText);

        int size = 6;
        int index = 0;
        do{
            if (guiPages.size() == 0 || guiPages.get(guiPages.size() - 1).getFreeSpaces().length == 0) {
                ChestGui current = new ChestGui(plugin, listEvent.title(guiPages.size() + 1, items.length <= 0 ? 1 : (int) Math.ceil(items.length / (double) size)), size);

                Arrays.stream(listEvent.buildHeader(this, current, guiPages.size() - 1, backGui, searchText)).forEach(current::addGuiItem);
                StaticItem[] footer = listEvent.buildFooter(this, current, guiPages.size() - 1, backGui, searchText);
                IntStream.range(0, footer.length).forEach(i -> current.getGuiItems().put(current.getSize() - 1 - i, footer[i]));
                guiPages.add(current);
            }
            if(items.length > index) {
                StaticItem item = items[index];
                guiPages.get(guiPages.size() - 1).addGuiItem(item);
            }
            index++;
        } while(items.length > index);

        return guiPages.toArray(new ChestGui[0]);
    }

    public boolean isSearch() {
        return search;
    }
    public boolean isCreate() {
        return createEvent != null;
    }

    public GuiItemEvent getCreateEvent() {
        return createEvent;
    }

    public JsonObject getGuiTranslation() {
        return guiTranslation;
    }
}
