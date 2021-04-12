package com.github.codedoctorde.api.ui.template.gui.events;

import com.github.codedoctorde.api.ui.StaticItem;
import com.github.codedoctorde.api.ui.template.gui.ListGui;
import com.github.codedoctorde.api.ui.ChestGui;
import com.github.codedoctorde.api.utils.ItemStackBuilder;
import com.google.gson.JsonObject;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public interface GuiListEvent {
    String title(int index, int size);

    StaticItem[] pages(String output);

    default StaticItem[] buildFooter(ListGui listGui, ChestGui gui, int currentPages, ChestGui backGui, String searchText){
        return new StaticItem[0];
    }

    default StaticItem[] buildHeader(ListGui listGui, ChestGui gui, int currentPages, ChestGui backGui, String searchText){
        JsonObject guiTranslation = listGui.getGuiTranslation();
        StaticItem placeholder = new StaticItem(new ItemStackBuilder(guiTranslation.getAsJsonObject("placeholder")).build());
        StaticItem search = new StaticItem(new ItemStackBuilder(guiTranslation.getAsJsonObject("search")).format(searchText).build(), new GuiItemEvent() {
            @Override
            public void onEvent(ChestGui gui, StaticItem guiItem, InventoryClickEvent event) {
                Player player = (Player) event.getWhoClicked();
                player.sendMessage(guiTranslation.getAsJsonObject("search").get("refresh").getAsString());
                listGui.createGuis(backGui, searchText)[0].open(player);
            }
        });
        StaticItem create = new StaticItem(new ItemStackBuilder(guiTranslation.getAsJsonObject("create")).build(), listGui.getCreateEvent());
        return new StaticItem[]{
                new StaticItem(new ItemStackBuilder(guiTranslation.getAsJsonObject("first")).build(), new GuiItemEvent() {

                    @Override
                    public void onEvent(ChestGui gui, StaticItem staticItem, InventoryClickEvent event) {
                        Player player = (Player) event.getWhoClicked();
                        if (currentPages <= 0)
                            player.sendMessage(guiTranslation.getAsJsonObject("first").get("already").getAsString());
                        else
                            listGui.createGuis(backGui, searchText)[0].open(player);
                    }
                }),
                new StaticItem(new ItemStackBuilder(guiTranslation.getAsJsonObject("previous")).build(), new GuiItemEvent() {

                    @Override
                    public void onEvent(ChestGui gui, StaticItem staticItem, InventoryClickEvent event) {
                        Player player = (Player) event.getWhoClicked();
                        if (currentPages <= 0)
                            player.sendMessage(guiTranslation.getAsJsonObject("previous").get("already").getAsString());
                        else
                            listGui.createGuis(backGui, searchText)[currentPages - 1].open(player);
                    }
                }),
                placeholder,
                new StaticItem(new ItemStackBuilder(guiTranslation.getAsJsonObject("back")).build(), new GuiItemEvent() {

                    @Override
                    public void onEvent(ChestGui gui, StaticItem staticItem, InventoryClickEvent event) {
                        Player player = (Player) event.getWhoClicked();
                        if (backGui != null)
                            backGui.open(player);
                        else
                            gui.close(player);
                    }
                }),
                listGui.isSearch() && listGui.isCreate()?search:placeholder,
                listGui.isCreate()?create:listGui.isSearch()?search:placeholder,
                placeholder,
                new StaticItem(new ItemStackBuilder(guiTranslation.getAsJsonObject("next")).build(), new GuiItemEvent() {

                    @Override
                    public void onEvent(ChestGui gui, StaticItem staticItem, InventoryClickEvent event) {
                        Player player = (Player) event.getWhoClicked();
                        ChestGui[] guis = listGui.createGuis(backGui, searchText);
                        if (currentPages >= guis.length - 1)
                            player.sendMessage(guiTranslation.getAsJsonObject("next").get("already").getAsString());
                        else
                            guis[currentPages + 1].open(player);
                    }
                }),
                new StaticItem(new ItemStackBuilder(guiTranslation.getAsJsonObject("last")).build(), new GuiItemEvent() {

                    @Override
                    public void onEvent(ChestGui gui, StaticItem staticItem, InventoryClickEvent event) {
                        Player player = (Player) event.getWhoClicked();
                        ChestGui[] guis = listGui.createGuis(backGui, searchText);
                        if (currentPages >= guis.length - 1)
                            player.sendMessage(guiTranslation.getAsJsonObject("last").get("already").getAsString());
                        else
                            guis[guis.length - 1].open(player);
                    }
                })

        };
    }
}
