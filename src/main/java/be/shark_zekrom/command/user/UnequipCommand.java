package be.shark_zekrom.command.user;

import be.shark_zekrom.command.SubCommand;
import be.shark_zekrom.balloons.BallonService;
import be.shark_zekrom.config.ConfigManager;
import org.bukkit.entity.Player;

import java.util.List;

public class UnequipCommand extends SubCommand {
    @Override
    public String getName() {
        return "unequip";
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
        return "balloonsplus.unequip";
    }

    @Override
    public int getArgsRequired() {
        return 0;
    }

    @Override
    public void perform(Player sender, String[] args) {
        BallonService ballonService = BallonService.getInstance();
        if (ballonService.hasBalloon(sender)) {
            ballonService.removeBalloon(sender);
            sender.sendMessage(ConfigManager.getInstance().getConfig("Settings.yml")
                    .getString("Messages.BalloonsRemoved","§bBalloons removed."));
        }
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
