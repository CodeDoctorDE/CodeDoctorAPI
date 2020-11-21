package tk.codedoctor.minecraft.ui.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public interface InventoryGuiItemEvent {
    default void onEvent(InventoryGui gui, InventoryGuiItem guiItem, InventoryClickEvent event) {
    }

    default void onTick(InventoryGui gui, InventoryGuiItem guiItem, Player player) {

    }

    default void onInteract(InventoryGui gui, InventoryGuiItem guiItem, PlayerInteractEvent event) {

    }

    default boolean onItemChange(InventoryGui gui, InventoryGuiItem guiItem, Player player, ItemStack change) {
        return false;
    }
}
