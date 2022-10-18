package be.shark_zekrom.command.user;

import be.shark_zekrom.command.SubCommand;
import be.shark_zekrom.gui.BalloonsGUI;
import org.bukkit.entity.Player;

import java.util.List;

public class EquipCommand extends SubCommand {
    @Override
    public String getName() {
        return "equip";
    }

    @Override
    public String getDescription() {
        return "null";
    }

    @Override
    public String getSyntax() {
        return "null";
    }

    @Override
    public String getPermission() {
        return "ballonsplus.equip";
    }

    @Override
    public int getArgsRequired() {
        return 0;
    }

    @Override
    public void perform(Player sender, String[] args) {
        //TODO
        //Menu.inventory(sender, 0);
        new BalloonsGUI(sender);
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
