package tk.codedoctor.minecraft.ui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public interface GuiItemEvent {
    default void onEvent(Gui gui, GuiItem guiItem, InventoryClickEvent event) {
    }

    default void onTick(Gui gui, GuiItem guiItem, Player player) {

    }
}
