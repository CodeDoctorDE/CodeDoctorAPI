package dev.linwood.api.gui.action;

import org.bukkit.command.CommandSender;

public interface CommandAction extends HandledAction {
    void handleCommand(final CommandSender sender, final String[] args);
    String[] onTabComplete(final CommandSender commandSender, final String[] args);
}
