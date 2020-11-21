package tk.codedoctor.minecraft.ui;

import org.bukkit.entity.Player;

public interface GuiEvent {
    default void onTick(Gui gui, Player player) {

    }

    default void onOpen(Gui gui, Player player) {

    }

    default void onClose(Gui gui, Player player) {

    }
}
