package dev.linwood.api.command;

import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public abstract class SubCommandManager implements SubCommand {
    @Override
    public abstract @NotNull List<String> aliases();

    @Override
    public @NotNull List<String> onTabComplete(CommandSender commandSender, String @NotNull [] args) {
        List<String> subCommandArgs = new ArrayList<>();
        List<SubCommand> subCommands = subCommands(commandSender, args);
        Collections.addAll(subCommandArgs, args);
        if (subCommandArgs.size() <= 0) return new ArrayList<>();
        else if (subCommandArgs.size() == 1) {
            List<String> tabCompletes = subCommands.stream().filter(Objects::nonNull).flatMap(subCommand -> subCommand.aliases().stream()).collect(Collectors.toList());
            return StringUtil.copyPartialMatches(args[0], tabCompletes, new ArrayList<>());
        } else {
            List<String> tabCompletes;
            subCommandArgs.remove(0);
            tabCompletes = subCommands.stream().filter(subCommand -> subCommand.aliases().contains(args[0])).map(subCommand -> subCommand.onTabComplete(commandSender, subCommandArgs.toArray(new String[0]))).filter(Objects::nonNull).flatMap(Collection::stream).collect(Collectors.toList());
            return StringUtil.copyPartialMatches(args[args.length - 1], tabCompletes, new ArrayList<>());
        }
    }

    public abstract @NotNull List<SubCommand> subCommands(CommandSender commandSender, String[] args);

    public abstract void onNoPermission(CommandSender commandSender, String[] args);

    protected abstract void onNoSubCommand(CommandSender commandSender, String[] args);

    @Override
    public void onCommand(@NotNull CommandSender commandSender, @NotNull String[] args) {
        var subCommandArgs = Arrays.asList(args).subList(args.length > 0 ? 1 : 0, args.length).toArray(String[]::new);
        List<SubCommand> subCommands = subCommands(commandSender, args);
        boolean exist = false;
        final var label = args.length > 0 ? args[0] : "";
        var permission = permission();
        if(permission != null && !commandSender.hasPermission(permission))
            onNoPermission(commandSender, args);
        subCommands.stream().filter(subCommand -> subCommand.aliases().contains(label)).findFirst().ifPresentOrElse(subCommand -> {
            var subPermission = subCommand.permission();
            if(subPermission == null || commandSender.hasPermission(subPermission))
                subCommand.onCommand(commandSender, subCommandArgs);
            else
                onNoPermission(commandSender, args);
        }, () -> onNoSubCommand(commandSender, args));
    }
}
