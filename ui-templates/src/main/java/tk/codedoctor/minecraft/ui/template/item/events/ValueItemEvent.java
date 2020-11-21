package tk.codedoctor.minecraft.ui.template.item.events;

import tk.codedoctor.minecraft.ui.template.item.ValueItem;
import tk.codedoctor.minecraft.ui.Gui;
import tk.codedoctor.minecraft.ui.GuiItem;
import org.bukkit.entity.Player;

/**
 * @author CodeDoctorDE
 */
public interface ValueItemEvent {
    boolean onEvent(float value, Player player, ValueItem valueItem);

    default void onTick(Gui gui, GuiItem guiItem, Player player) {

    }
}
