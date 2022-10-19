package be.shark_zekrom.command.admin;

import be.shark_zekrom.command.SubCommand;
import be.shark_zekrom.config.ConfigManager;
import be.shark_zekrom.messages.Message;
import be.shark_zekrom.messages.MessageService;
import org.bukkit.entity.Player;

import java.util.List;

public class ReloadCommand extends SubCommand {
    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "Reload Settings.yml";
    }

    @Override
    public String getSyntax() {
        return "/balloonsplus reload";
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
        MessageService.getInstance().reload();
        Message.CONFIG_RELOAD.send(sender);
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
