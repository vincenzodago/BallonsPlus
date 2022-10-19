package be.shark_zekrom.command.user;

import be.shark_zekrom.command.SubCommand;
import be.shark_zekrom.balloons.BalloonService;
import org.bukkit.entity.Player;

import java.util.List;

public class UnequipCommand extends SubCommand {
    @Override
    public String getName() {
        return "unequip";
    }

    @Override
    public String getDescription() {
        return "Unequip an equipped balloon";
    }

    @Override
    public String getSyntax() {
        return "/balloonsplus unequip";
    }

    @Override
    public String getPermission() {
        return "balloonsplus.unequip";
    }

    @Override
    public int getArgsRequired() {
        return 0;
    }

    @Override
    public void perform(Player sender, String[] args) {
        BalloonService balloonService = BalloonService.getInstance();
        balloonService.removeBalloon(sender);
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
