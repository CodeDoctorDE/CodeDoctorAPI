package tk.codedoctor.minecraft.ui;

import tk.codedoctor.minecraft.utils.ItemStackBuilder;
import com.google.gson.JsonObject;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * @author CodeDoctorDE
 */
public class GuiItem {
    private ItemStack itemStack;
    private final GuiItemEvent guiItemEvent;

    public GuiItem(final ItemStack itemStack, final GuiItemEvent guiItemEvent) {
        this.itemStack = itemStack;
        if(guiItemEvent != null)
            this.guiItemEvent = guiItemEvent;
        else
            this.guiItemEvent = new GuiItemEvent() {
            };
    }

    public GuiItem(final ItemStackBuilder itemStackBuilder, final GuiItemEvent guiItemEvent) {
        this(itemStackBuilder.build(), guiItemEvent);
    }

    public GuiItem(final JsonObject jsonObject, final GuiItemEvent guiItemEvent) {
        this(new ItemStackBuilder(jsonObject).build(), guiItemEvent);
    }

    public GuiItem(final GuiItemEvent guiItemEvent) {
        this(new ItemStack(Material.AIR), guiItemEvent);
    }

    public GuiItem(final ItemStack itemStack) {
        this(itemStack, null);
    }

    public GuiItem(final ItemStackBuilder itemStackBuilder) {
        this(itemStackBuilder.build(), null);
    }

    public GuiItem(final JsonObject jsonObject) {
        this(new ItemStackBuilder(jsonObject).build(), null);
    }

    public void raiseEvent(final Gui gui, final InventoryClickEvent event) {
        guiItemEvent.onEvent(gui, this, event);
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public void runTick(Gui gui, Player player) {
        guiItemEvent.onTick(gui, this, player);
    }
}
