package com.github.codedoctorde.api.ui.template.gui;

import com.github.codedoctorde.api.ui.Gui;
import com.github.codedoctorde.api.ui.GuiEvent;
import com.github.codedoctorde.api.ui.GuiItem;
import com.github.codedoctorde.api.ui.GuiItemEvent;
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

    public Gui[] createGuis() {
        return createGuis(null, "");
    }

    public Gui[] createGuis(String searchText) {
        return createGuis(null, searchText);
    }

    public Gui[] createGuis(Gui backGui) {
        return createGuis(backGui, "");
    }

    public Gui[] createGuis(Gui backGui, String searchText) {
        List<Gui> guiPages = new ArrayList<>();
        GuiItem[] items = listEvent.pages(searchText);

        int size = 0;
        for (GuiItem item : items) {
            if (guiPages.size() == 0 || guiPages.get(guiPages.size() - 1).getFreeSpaces().length == 0) {
                Gui current = new Gui(plugin, listEvent.title(guiPages.size()), size);

                Arrays.stream(listEvent.buildHeader(this, current, guiPages.size() - 1, backGui, searchText)).forEach(current::addGuiItem);
                GuiItem[] footer = listEvent.buildFooter(this, current, guiPages.size() - 1, backGui, searchText);
                IntStream.range(0, footer.length).forEach(i -> current.putGuiItem(current.getSize() - 1 - i, footer[i]));
                guiPages.add(current);
            }
            guiPages.get(guiPages.size() - 1).addGuiItem(item);
        }

        return guiPages.toArray(new Gui[0]);
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
