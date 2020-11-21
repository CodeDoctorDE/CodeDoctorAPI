package tk.codedoctor.minecraft.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class PluginCommandManager implements TabCompleter, CommandExecutor {

    public boolean onCommand(CommandSender commandSender, Command command, String s,
                             String[] args) {
        List<String> subCommandArgs = new ArrayList<>(Arrays.asList(args));
        List<SubCommand> subCommands = subCommands(commandSender, args);
        boolean exist = false;
        if (args.length <= 0) {
            for (SubCommand subCommand :
                    subCommands)
                if (subCommand.aliases() != null)
                    if (subCommand.aliases().contains("")) {
                        exist = true;
                        if (commandSender.hasPermission(subCommand.permission()))
                            subCommand.onCommand(commandSender, subCommandArgs.toArray(new String[0]));
                        else
                            onNoPermission(commandSender, args);
                    }
        } else {
            subCommandArgs.remove(0);
            for (SubCommand subCommand :
                    subCommands)
                if (subCommand.aliases() != null)
                    if (subCommand.aliases().contains(args[0])) {
                        exist = true;
                        if (commandSender.hasPermission(subCommand.permission()))
                            subCommand.onCommand(commandSender, subCommandArgs.toArray(new String[0]));
                        else
                            onNoPermission(commandSender, args);
                    }
        }
        if (!exist)
            onNoSubCommand(commandSender, args);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        List<String> subCommandArgs = new ArrayList<>();
        List<SubCommand> subCommands = subCommands(commandSender, args);
        Collections.addAll(subCommandArgs, args);
        if (subCommandArgs.size() <= 0) {
            return new ArrayList<>();
        } else if (subCommandArgs.size() == 1) {
            List<String> tabCompletes = new ArrayList<>();
            for (SubCommand subCommand :
                    subCommands) {
                if (subCommand != null)
                    if (subCommand.aliases() != null)
                        tabCompletes.addAll(subCommand.aliases());
            }
            return StringUtil.copyPartialMatches(args[0], tabCompletes, new ArrayList<>());
        } else {
            List<String> tabCompletes = new ArrayList<>();
            subCommandArgs.remove(0);
            for (SubCommand subCommand :
                    subCommands) {
                if (subCommand.aliases().contains(args[0])) {
                    List<String> currentTabCompletes = subCommand.onTabComplete(commandSender, subCommandArgs.toArray(new String[0]));
                    if (currentTabCompletes != null)
                        tabCompletes.addAll(currentTabCompletes);
                }
            }
            return StringUtil.copyPartialMatches(args[args.length - 1], tabCompletes, new ArrayList<>());
        }
    }

    protected abstract void onNoPermission(CommandSender player, String[] args);

    protected abstract void onNoSubCommand(CommandSender player, String[] args);

    protected abstract List<SubCommand> subCommands(CommandSender player, String[] args);
}
