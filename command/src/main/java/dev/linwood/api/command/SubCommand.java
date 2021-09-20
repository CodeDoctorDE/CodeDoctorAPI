package dev.linwood.api.command;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface SubCommand {
    default @NotNull List<String> aliases() {
        return List.of("");
    }

    default List<String> onTabComplete(final CommandSender commandSender, final String[] args) {
        return new ArrayList<>();
    }

    @Nullable default String permission() {
        return null;
    }

    void onCommand(final CommandSender commandSender, final String[] args);
}
