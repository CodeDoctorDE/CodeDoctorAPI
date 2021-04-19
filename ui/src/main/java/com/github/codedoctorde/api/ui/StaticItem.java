package com.github.codedoctorde.api.ui;

import com.github.codedoctorde.api.utils.ItemStackBuilder;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author CodeDoctorDE
 */
public class StaticItem implements GuiItem {
    private ItemStack itemStack;
    private final List<Object> placeholders = new ArrayList<>();
    private Consumer<InventoryClickEvent> clickAction;
    private Runnable renderAction;

    public StaticItem(final ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public void raiseEvent(final InventoryClickEvent event) {
        clickAction.accept(event);
    }

    public void setClickAction(Consumer<InventoryClickEvent> clickAction) {
        this.clickAction = clickAction;
    }

    public void setRenderAction(Runnable renderAction) {
        this.renderAction = renderAction;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }
    public void setPlaceholders(Object... placeholders){
        this.placeholders.clear();
        this.placeholders.addAll(Collections.singleton(placeholders));
    }

    public Object[] getPlaceholders() {
        return placeholders.toArray();
    }
    @Override
    public ItemStack build() {
        renderAction.run();
        return new ItemStackBuilder(itemStack).format(placeholders).build();
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        clickAction.accept(event);
    }
}
