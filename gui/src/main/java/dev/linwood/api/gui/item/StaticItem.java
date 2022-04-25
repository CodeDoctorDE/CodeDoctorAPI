package dev.linwood.api.gui.item;

import dev.linwood.api.gui.Gui;
import dev.linwood.api.item.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * Create a new normal item which can be added in the gui
 *
 * @author CodeDoctorDE
 */
public class StaticItem implements GuiItem {
    protected Consumer<InventoryClickEvent> clickAction = (event) -> {
    };
    protected Consumer<Gui> renderAction = (gui) -> {
    };
    private ItemStack itemStack;
    private @NotNull Object[] placeholders = new Object[0];

    public StaticItem() {
        this(new ItemStack(Material.AIR));
    }

    public StaticItem(final ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    /**
     * Set the action which will be perform if the player clicks on this item
     *
     * @param clickAction The method which will be called
     */
    public void setClickAction(Consumer<InventoryClickEvent> clickAction) {
        this.clickAction = clickAction;
    }

    /**
     * Set the action which will be perform if the gui opens
     *
     * @param renderAction The method which will be called
     */
    public void setRenderAction(Consumer<Gui> renderAction) {
        this.renderAction = renderAction;
    }

    /**
     * Get the current item stack of the item
     *
     * @return The rendered item stack
     */
    public ItemStack getItemStack() {
        return itemStack;
    }

    /**
     * Set the item stack of the gui item
     * This does not perform a rerender of the gui
     *
     * @param itemStack The item stack which will be rendered
     */
    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    /**
     * Get all placeholders
     *
     * @return Returns the placeholder list
     */
    public Object[] getPlaceholders() {
        return placeholders;
    }

    /**
     * Set the placeholder which will formatted the itemstack
     *
     * @param placeholders All placeholders which can be replaced from the display name and the lore
     */
    public void setPlaceholders(Object... placeholders) {
        this.placeholders = placeholders;
    }

    /**
     * Build the item stack
     *
     * @return The current item stack with the formatted placeholders
     */
    @Override
    public ItemStack build(Gui gui) {
        renderAction.accept(gui);
        return new ItemStackBuilder(itemStack).format(placeholders).build();
    }

    /**
     * Call the click action
     *
     * @param event The inventory event
     */
    @Override
    public void onClick(InventoryClickEvent event) {
        clickAction.accept(event);
    }
}
