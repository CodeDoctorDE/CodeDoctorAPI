package com.gitlab.codedoctorde.api.ui.template;

import com.gitlab.codedoctorde.api.ui.Gui;
import com.gitlab.codedoctorde.api.ui.GuiItem;
import com.gitlab.codedoctorde.api.ui.GuiItemEvent;
import com.gitlab.codedoctorde.api.utils.ItemStackBuilder;
import com.google.gson.JsonObject;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * @author CodeDoctorDE
 */
public class JsonGuiItem {
    private final JsonObject jsonObject;
    private final JsonGuiItemEvent itemEvent;

    public JsonGuiItem(JsonObject jsonObject, JsonGuiItemEvent itemEvent) {
        this.jsonObject = jsonObject;
        this.itemEvent = itemEvent;
    }

    public GuiItem build() {
        return new GuiItem(new ItemStackBuilder(jsonObject), new GuiItemEvent() {
            @Override
            public void onEvent(Gui gui, GuiItem guiItem, InventoryClickEvent event) {
                itemEvent.onEvent(gui, guiItem, event, jsonObject);
            }

            @Override
            public void onTick(Gui gui, GuiItem guiItem, Player player) {
                itemEvent.onTick(gui, guiItem, player, jsonObject);
            }

        });
    }

    public interface JsonGuiItemEvent {
        default void onEvent(Gui gui, GuiItem guiItem, InventoryClickEvent event, JsonObject jsonObject) {
        }

        default void onTick(Gui gui, GuiItem guiItem, Player player, JsonObject jsonObject) {

        }

        default boolean onItemChange(Gui gui, GuiItem guiItem, Player player, ItemStack change, JsonObject jsonObject) {
            return false;
        }
    }
}
