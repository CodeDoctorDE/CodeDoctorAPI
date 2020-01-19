package com.gitlab.codedoctorde.api.ui.template;

import com.gitlab.codedoctorde.api.ui.Gui;
import com.gitlab.codedoctorde.api.ui.GuiEvent;
import com.gitlab.codedoctorde.api.ui.GuiItem;
import com.gitlab.codedoctorde.api.ui.GuiItemEvent;
import com.gitlab.codedoctorde.api.ui.template.events.GuiListEvent;
import com.gitlab.codedoctorde.api.utils.ItemStackBuilder;
import com.google.gson.JsonObject;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class ListGui {
    private final GuiItemEvent createEvent;
    private final GuiListEvent listEvent;
    private final JavaPlugin plugin;
    private final GuiEvent guiEvent;

    public ListGui(JavaPlugin plugin, GuiItemEvent createEvent, GuiListEvent listEvent, GuiEvent guiEvent) {
        this.plugin = plugin;
        this.createEvent = createEvent;
        this.listEvent = listEvent;
        this.guiEvent = guiEvent;
    }

    public Gui[] createGui(JsonObject guiTranslation, Gui backGui) {
        return createGui(guiTranslation, backGui, "");
    }

    public Gui[] createGui(JsonObject guiTranslation, Gui backGui, String searchText) {
        List<Gui> guiPages = new ArrayList<>();
        GuiItem[] items = listEvent.pages(searchText);
        List<List<GuiItem>> pages = new ArrayList<>();
        for (int i = 0; i < items.length; i++) {
            if (i % 36 == 0)
                pages.add(new ArrayList<>());
            pages.get(pages.size() - 1).add(items[i]);
        }
        if (pages.size() == 0)
            pages.add(new ArrayList<>());
        for (int i = 0; i < pages.size(); i++) {
            int finalI = i;
            guiPages.add(new Gui(plugin, listEvent.title(i, pages.size()), 5, guiEvent) {
                {
                    GuiItem placeholder = new GuiItem(new ItemStackBuilder(guiTranslation.getAsJsonObject("placeholder").getAsJsonObject()).build());
                    getGuiItems().put(0, new GuiItem(new ItemStackBuilder(guiTranslation.getAsJsonObject("first").getAsJsonObject()).build(), new GuiItemEvent() {

                        @Override
                        public void onEvent(Gui gui, GuiItem guiItem, InventoryClickEvent event) {
                            Player player = (Player) event.getWhoClicked();
                            if (finalI <= 0)
                                player.sendMessage(guiTranslation.getAsJsonObject("first").getAsJsonObject("already").getAsString());
                            else
                                createGui(guiTranslation, backGui, searchText)[0].open(player);
                        }

                        @Override
                        public void onTick(Gui gui, GuiItem guiItem, Player player) {

                        }
                    }));
                    getGuiItems().put(1, new GuiItem(new ItemStackBuilder(guiTranslation.getAsJsonObject("previous").getAsJsonObject()).build(), new GuiItemEvent() {

                        @Override
                        public void onEvent(Gui gui, GuiItem guiItem, InventoryClickEvent event) {
                            Player player = (Player) event.getWhoClicked();
                            if (finalI <= 0)
                                player.sendMessage(guiTranslation.getAsJsonObject("previous").getAsJsonObject("already").getAsString());
                            else
                                createGui(guiTranslation, backGui, searchText)[finalI - 1].open(player);
                        }

                        @Override
                        public void onTick(Gui gui, GuiItem guiItem, Player player) {

                        }
                    }));
                    getGuiItems().put(2, placeholder);
                    getGuiItems().put(3, new GuiItem(new ItemStackBuilder(guiTranslation.getAsJsonObject("back").getAsJsonObject()).build(), new GuiItemEvent() {

                        @Override
                        public void onEvent(Gui gui, GuiItem guiItem, InventoryClickEvent event) {
                            Player player = (Player) event.getWhoClicked();
                            backGui.open(player);
                        }
                    }));

                    getGuiItems().put(4, new GuiItem(new ItemStackBuilder(guiTranslation.getAsJsonObject("search").getAsJsonObject()).build(), new GuiItemEvent() {
                        @Override
                        public void onEvent(Gui gui, GuiItem guiItem, InventoryClickEvent event) {
                            Player player = (Player) event.getWhoClicked();
                            player.sendMessage(guiTranslation.getAsJsonObject("search").getAsJsonObject("refresh").getAsString());
                            createGui(guiTranslation, backGui, searchText)[0].open(player);
                        }
                    }));
                    getGuiItems().put(5, new GuiItem(new ItemStackBuilder(guiTranslation.getAsJsonObject("create").getAsJsonObject()).build(), createEvent));
                    getGuiItems().put(6, placeholder);
                    getGuiItems().put(7, new GuiItem(new ItemStackBuilder(guiTranslation.getAsJsonObject("next").getAsJsonObject()).build(), new GuiItemEvent() {

                        @Override
                        public void onEvent(Gui gui, GuiItem guiItem, InventoryClickEvent event) {
                            Player player = (Player) event.getWhoClicked();
                            if (finalI >= pages.size() - 1)
                                player.sendMessage(guiTranslation.getAsJsonObject("next").getAsJsonObject("already").getAsString());
                            else
                                createGui(guiTranslation, backGui, searchText)[finalI + 1].open(player);
                        }

                        @Override
                        public void onTick(Gui gui, GuiItem guiItem, Player player) {

                        }
                    }));
                    getGuiItems().put(8, new GuiItem(new ItemStackBuilder(guiTranslation.getAsJsonObject("last").getAsJsonObject()).build(), new GuiItemEvent() {

                        @Override
                        public void onEvent(Gui gui, GuiItem guiItem, InventoryClickEvent event) {
                            Player player = (Player) event.getWhoClicked();
                            if (finalI >= pages.size() - 1)
                                player.sendMessage(guiTranslation.getAsJsonObject("last").getAsJsonObject("already").getAsString());
                            else
                                createGui(guiTranslation, backGui, searchText)[pages.size() - 1].open(player);
                        }

                        @Override
                        public void onTick(Gui gui, GuiItem guiItem, Player player) {

                        }
                    }));
                }
            });
        }
        return guiPages.toArray(new Gui[0]);
    }
}