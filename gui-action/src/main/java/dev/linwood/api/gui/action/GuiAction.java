package dev.linwood.api.gui.action;

import dev.linwood.api.gui.Gui;
import org.bukkit.entity.Player;

public interface GuiAction extends HandledAction {
    public Gui getGui();
    default void openGui(Player player) {

    }
}
