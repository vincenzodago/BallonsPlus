package be.shark_zekrom.command;

import org.bukkit.entity.Player;

import java.util.List;

public abstract class SubCommand {

    public abstract String getName();

    public abstract String getDescription();

    public abstract String getSyntax();

    public abstract String getPermission();

    public abstract int getArgsRequired();

    public abstract void perform(Player sender, String[] args);

    public abstract List<String> getSubcommandArguments(Player player, String[] args);

}
