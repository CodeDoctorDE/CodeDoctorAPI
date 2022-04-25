package dev.linwood.api.gui.action;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Objects;

public interface ActionManager extends CommandAction {
    HandledAction[] getActions();
    @Override
    default String[] onTabComplete(final CommandSender commandSender, final String[] args) {
        return Arrays.stream(getActions()).flatMap(handledAction -> Arrays.stream(handledAction.getAliases())).toArray(String[]::new);
    }

    @Override
    default void handleCommand(final CommandSender sender, final String[] args) {
        if(args.length == 0) {
            if (this instanceof GuiAction) {
                ((GuiAction) this).openGui((Player) sender);
            } else {
                onUnknownAction(sender, args);
            }
        }
        Arrays.stream(getActions()).filter(handledAction -> Arrays.stream(handledAction.getAliases()).anyMatch(s -> Objects.equals(s, args[0]))).findFirst()
                .ifPresentOrElse(handledAction -> {
                    if(handledAction instanceof GuiAction && sender instanceof Player) {
                        ((GuiAction) handledAction).openGui((Player) sender);
                    }
                    if(handledAction instanceof CommandAction) {
                        ((CommandAction) handledAction).handleCommand(sender, Arrays.copyOfRange(args, 1, args.length));
                    }
                }, () -> onUnknownAction(sender, args));
    }

    void onUnknownAction(final CommandSender sender, String[] args);
}
