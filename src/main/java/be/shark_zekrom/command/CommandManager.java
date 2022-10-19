package be.shark_zekrom.command;

import be.shark_zekrom.command.admin.ReloadCommand;
import be.shark_zekrom.command.user.EquipCommand;
import be.shark_zekrom.command.user.UnequipCommand;
import be.shark_zekrom.messages.Message;
import be.shark_zekrom.utils.NameUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements TabExecutor {

    private final ArrayList<SubCommand> subcommands = new ArrayList<>();

    public CommandManager() {
        subcommands.add(new UnequipCommand());
        subcommands.add(new EquipCommand());
        subcommands.add(new ReloadCommand());
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Error: " + ChatColor.RESET + " can't run commands from console");
            return true;
        }

        Player p = (Player) sender;

        if (args.length > 0) {
            for (int i = 0; i < getSubcommands().size(); i++) {
                if (args[0].equalsIgnoreCase(getSubcommands().get(i).getName())) {

                    SubCommand subCommand = getSubcommands().get(i);

                    if (!sender.hasPermission(subCommand.getPermission())) {
                        Message.BALLOONS_NO_CMD_PERM.send(sender);
                        return true;
                    }

                    if (args.length < subCommand.getArgsRequired()) {
                        sender.sendMessage(ChatColor.YELLOW + "Error: " + ChatColor.RESET
                                + " not enough arguments");
                        return true;
                    }

                    subCommand.perform(p, args);

                }
            }
        } else {

            for (int i = 0; i < getSubcommands().size(); i++) {

                SubCommand subCommand = getSubcommands().get(i);
                if (!sender.hasPermission(subCommand.getPermission()))
                    continue;
                sender.sendMessage(ChatColor.YELLOW + subCommand.getSyntax()
                        + "\n" + ChatColor.AQUA + subCommand.getDescription());

            }
        }

        return true;
    }

    public ArrayList<SubCommand> getSubcommands(){
        return subcommands;
    }


    @SuppressWarnings("NullableProblems")
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        String argChar;

        if (args.length == 1) {
            argChar = args[0];

            ArrayList<String> subcommandsArguments = new ArrayList<>();

            for (int i = 0; i < getSubcommands().size(); i++) {
                SubCommand subCommand = getSubcommands().get(i);

                if (!sender.hasPermission(subCommand.getPermission()))
                    continue;

                subcommandsArguments.add(subCommand.getName());
            }

            return NameUtil.filterByStart(subcommandsArguments,argChar);

        }else if (args.length >= 2) {
            argChar = args[args.length - 1];

            for (int i = 0; i < getSubcommands().size(); i++) {
                if (args[0].equalsIgnoreCase(getSubcommands().get(i).getName())) {
                    return NameUtil.filterByStart(getSubcommands().get(i).getSubcommandArguments((Player) sender, args),argChar);
                }
            }
        }

        return null;
    }
}
