package com.github.codedoctorde.api.ui.template.item;

import com.github.codedoctorde.api.ui.ChestGui;
import com.github.codedoctorde.api.ui.StaticItem;
import com.github.codedoctorde.api.ui.template.item.events.InputItemEvent;
import com.github.codedoctorde.api.utils.ItemStackBuilder;
import com.google.gson.JsonObject;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InputItem {
    protected final ItemStack itemStack;
    private final InputItemEvent inputItemEvent;
    protected Object[] format;

    public InputItem(ItemStack itemStack, InputItemEvent inputItemEvent){
        this.itemStack = itemStack;
        this.inputItemEvent = inputItemEvent;
    }
    public InputItem(ItemStackBuilder itemStackBuilder, InputItemEvent inputItemEvent){
        this(itemStackBuilder.build(), inputItemEvent);
    }
    public InputItem(JsonObject jsonObject, InputItemEvent inputItemEvent){
        this(new ItemStackBuilder(jsonObject).build(), inputItemEvent);
    }

    public InputItem setFormat(Object... format) {
        this.format = format;
        return this;
    }

    public Object[] getFormat() {
        return format;
    }

    public StaticItem build(){
        final InputItem inputItem = this;
        return new StaticItem(getFormattedItemStack(), new GuiItemEvent() {
            @Override
            public void onEvent(ChestGui gui, StaticItem staticItem, InventoryClickEvent event) {
                inputItemEvent.onEvent(gui, staticItem, event, inputItem);
            }

            @Override
            public void onTick(ChestGui gui, StaticItem staticItem, Player player) {
                inputItemEvent.onTick(gui, staticItem, player, inputItem);
            }
        });
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
    public ItemStack getFormattedItemStack(){
        return new ItemStackBuilder(itemStack.clone()).format(format).build();
    }
}
