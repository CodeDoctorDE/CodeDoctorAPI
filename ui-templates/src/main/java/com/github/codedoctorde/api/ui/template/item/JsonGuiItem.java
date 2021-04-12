package com.github.codedoctorde.api.ui.template.item;

import com.github.codedoctorde.api.ui.ChestGui;
import com.github.codedoctorde.api.ui.StaticItem;
import com.github.codedoctorde.api.utils.ItemStackBuilder;
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

    public StaticItem build() {
        return new StaticItem(new ItemStackBuilder(jsonObject), new GuiItemEvent() {
            @Override
            public void onEvent(ChestGui gui, StaticItem staticItem, InventoryClickEvent event) {
                itemEvent.onEvent(gui, staticItem, event, jsonObject);
            }

            @Override
            public void onTick(ChestGui gui, StaticItem staticItem, Player player) {
                itemEvent.onTick(gui, staticItem, player, jsonObject);
            }

        });
    }

    public interface JsonGuiItemEvent {
        default void onEvent(ChestGui gui, StaticItem staticItem, InventoryClickEvent event, JsonObject jsonObject) {
        }

        default void onTick(ChestGui gui, StaticItem staticItem, Player player, JsonObject jsonObject) {

        }

        default boolean onItemChange(ChestGui gui, StaticItem staticItem, Player player, ItemStack change, JsonObject jsonObject) {
            return false;
        }
    }
}
