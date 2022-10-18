package be.shark_zekrom.command.admin;

import be.shark_zekrom.command.SubCommand;
import be.shark_zekrom.config.ConfigManager;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

public class ReloadCommand extends SubCommand {
    @Override
    public String getName() {
        return "reload";
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
        return "balloonsplus.reload";
    }

    @Override
    public int getArgsRequired() {
        return 0;
    }

    @Override
    public void perform(Player sender, String[] args) {
        ConfigManager configManager = ConfigManager.getInstance();
        configManager.reloadConfigs();
        sender.sendMessage(Component.text(ConfigManager.getInstance()
                .getConfig("Settings.yml").getString("Messages.BalloonReload","§bSuccessfully reloaded!")));
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
