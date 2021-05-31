package com.github.codedoctorde.api.ui;

import com.github.codedoctorde.api.utils.ItemStackBuilder;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * Create a new normal item which can be added in the gui
 * @author CodeDoctorDE
 */
public class StaticItem implements GuiItem {
    private ItemStack itemStack;
    private List<Object> placeholders = new ArrayList<>();
    private Consumer<InventoryClickEvent> clickAction;
    private Runnable renderAction;

    public StaticItem(final ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    /**
     * Set the action which will be perform if the player clicks on this item
     * @param clickAction The method which will be called
     */
    public void setClickAction(Consumer<InventoryClickEvent> clickAction) {
        this.clickAction = clickAction;
    }

    /**
     * Set the action which will be perform if the gui opens
     * @param renderAction The method which will be called
     */
    public void setRenderAction(Runnable renderAction) {
        this.renderAction = renderAction;
    }

    /**
     * Get the current item stack of the item
     * @return The rendered item stack
     */
    public ItemStack getItemStack() {
        return itemStack;
    }

    /**
     * Set the item stack of the gui item
     * This does not perform a rerender of the gui
     * @param itemStack The item stack which will be rendered
     */
    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    /**
     * Set the placeholder which will formattedf the itemstack
     * @param placeholders All placeholders which can be replaced from the display name and the lore
     */
    public void setPlaceholders(Object... placeholders) {
        this.placeholders = Arrays.asList(placeholders);
    }

    /**
     * Get all placeholders
     * @return Returns the placeholder list
     */
    public Object[] getPlaceholders() {
        return placeholders.toArray();
    }

    /**
     * Build the item stack
     * @return The current item stack with the formatted placeholders
     */
    @Override
    public ItemStack build() {
        renderAction.run();
        return new ItemStackBuilder(itemStack).format(placeholders).build();
    }

    /**
     * Call the click action
     * @param event The inventory event
     */
    @Override
    public void onClick(InventoryClickEvent event) {
        clickAction.accept(event);
    }
}
