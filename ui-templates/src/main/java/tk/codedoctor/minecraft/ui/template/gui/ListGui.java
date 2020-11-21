package tk.codedoctor.minecraft.ui.template.gui;

import tk.codedoctor.minecraft.ui.Gui;
import tk.codedoctor.minecraft.ui.GuiEvent;
import tk.codedoctor.minecraft.ui.GuiItem;
import tk.codedoctor.minecraft.ui.GuiItemEvent;
import tk.codedoctor.minecraft.ui.template.gui.events.GuiListEvent;
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

        int size = 6;
        int index = 0;
        do{
            if (guiPages.size() == 0 || guiPages.get(guiPages.size() - 1).getFreeSpaces().length == 0) {
                Gui current = new Gui(plugin, listEvent.title(guiPages.size() + 1, items.length <= 0 ? 1 : (int) Math.ceil(items.length / (double) size)), size);

                Arrays.stream(listEvent.buildHeader(this, current, guiPages.size() - 1, backGui, searchText)).forEach(current::addGuiItem);
                GuiItem[] footer = listEvent.buildFooter(this, current, guiPages.size() - 1, backGui, searchText);
                IntStream.range(0, footer.length).forEach(i -> current.getGuiItems().put(current.getSize() - 1 - i, footer[i]));
                guiPages.add(current);
            }
            if(items.length > index) {
                GuiItem item = items[index];
                guiPages.get(guiPages.size() - 1).addGuiItem(item);
            }
            index++;
        } while(items.length > index);

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
