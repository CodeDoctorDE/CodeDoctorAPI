package tk.codedoctor.minecraft.ui.template.item.events;

import tk.codedoctor.minecraft.ui.template.item.InputItem;
import tk.codedoctor.minecraft.ui.Gui;
import tk.codedoctor.minecraft.ui.GuiItem;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public interface InputItemEvent {
    default void onEvent(Gui gui, GuiItem guiItem, InventoryClickEvent event, InputItem inputItem) {
    }

    default void onTick(Gui gui, GuiItem guiItem, Player player, InputItem inputItem) {

    }
}
