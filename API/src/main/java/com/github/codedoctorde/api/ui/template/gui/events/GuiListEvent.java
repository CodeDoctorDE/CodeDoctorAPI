package com.github.codedoctorde.api.ui.template.gui.events;

import com.github.codedoctorde.api.ui.Gui;
import com.github.codedoctorde.api.ui.GuiItem;
import com.github.codedoctorde.api.ui.GuiItemEvent;
import com.github.codedoctorde.api.ui.template.gui.ListGui;
import com.github.codedoctorde.api.utils.ItemStackBuilder;
import com.google.gson.JsonObject;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public interface GuiListEvent {
    String title(int index);

    GuiItem[] pages(String output);

    default GuiItem[] buildFooter(ListGui listGui, Gui gui, int currentPages, Gui backGui, String searchText){
        return new GuiItem[0];
    }

    default GuiItem[] buildHeader(ListGui listGui, Gui gui, int currentPages, Gui backGui, String searchText){
        JsonObject guiTranslation = listGui.getGuiTranslation();
        GuiItem placeholder = new GuiItem(new ItemStackBuilder(guiTranslation.getAsJsonObject("placeholder")).build());
        GuiItem search = new GuiItem(new ItemStackBuilder(guiTranslation.getAsJsonObject("search")).format(searchText).build(), new GuiItemEvent() {
            @Override
            public void onEvent(Gui gui, GuiItem guiItem, InventoryClickEvent event) {
                Player player = (Player) event.getWhoClicked();
                player.sendMessage(guiTranslation.getAsJsonObject("search").get("refresh").getAsString());
                listGui.createGui(backGui, searchText)[0].open(player);
            }
        });
        GuiItem create = new GuiItem(new ItemStackBuilder(guiTranslation.getAsJsonObject("create")).build(), listGui.getCreateEvent());
        return new GuiItem[]{
                new GuiItem(new ItemStackBuilder(guiTranslation.getAsJsonObject("first")).build(), new GuiItemEvent() {

                    @Override
                    public void onEvent(Gui gui, GuiItem guiItem, InventoryClickEvent event) {
                        Player player = (Player) event.getWhoClicked();
                        if (currentPages <= 0)
                            player.sendMessage(guiTranslation.getAsJsonObject("first").get("already").getAsString());
                        else
                            listGui.createGui(backGui, searchText)[0].open(player);
                    }
                }),
                new GuiItem(new ItemStackBuilder(guiTranslation.getAsJsonObject("previous")).build(), new GuiItemEvent() {

                    @Override
                    public void onEvent(Gui gui, GuiItem guiItem, InventoryClickEvent event) {
                        Player player = (Player) event.getWhoClicked();
                        if (currentPages <= 0)
                            player.sendMessage(guiTranslation.getAsJsonObject("previous").get("already").getAsString());
                        else
                            listGui.createGui(backGui, searchText)[currentPages - 1].open(player);
                    }
                }),
                placeholder,
                new GuiItem(new ItemStackBuilder(guiTranslation.getAsJsonObject("back")).build(), new GuiItemEvent() {

                    @Override
                    public void onEvent(Gui gui, GuiItem guiItem, InventoryClickEvent event) {
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
                new GuiItem(new ItemStackBuilder(guiTranslation.getAsJsonObject("next")).build(), new GuiItemEvent() {

                    @Override
                    public void onEvent(Gui gui, GuiItem guiItem, InventoryClickEvent event) {
                        Player player = (Player) event.getWhoClicked();
                        Gui[] guis = listGui.createGui(backGui, searchText);
                        if (currentPages >= guis.length - 1)
                            player.sendMessage(guiTranslation.getAsJsonObject("next").get("already").getAsString());
                        else
                            guis[currentPages + 1].open(player);
                    }
                }),
                new GuiItem(new ItemStackBuilder(guiTranslation.getAsJsonObject("last")).build(), new GuiItemEvent() {

                    @Override
                    public void onEvent(Gui gui, GuiItem guiItem, InventoryClickEvent event) {
                        Player player = (Player) event.getWhoClicked();
                        Gui[] guis = listGui.createGui(backGui, searchText);
                        if (currentPages >= guis.length - 1)
                            player.sendMessage(guiTranslation.getAsJsonObject("last").get("already").getAsString());
                        else
                            guis[guis.length - 1].open(player);
                    }
                })

        };
    }
}
