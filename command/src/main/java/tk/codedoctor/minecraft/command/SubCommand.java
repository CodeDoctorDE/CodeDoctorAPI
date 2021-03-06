package tk.codedoctor.minecraft.command;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface SubCommand {
    List<String> aliases();

    List<String> onTabComplete(final CommandSender commandSender, final String[] args);

    String permission();

    void onCommand(final CommandSender commandSender, final String[] args);
}
