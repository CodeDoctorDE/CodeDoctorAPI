package tk.codedoctor.minecraft.ui.template.gui.events;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface ItemCreatorEvent {
    void onEvent(Player player, ItemStack itemStack);
    void onCancel(Player player);
}
