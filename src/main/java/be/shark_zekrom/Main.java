package be.shark_zekrom;

import be.shark_zekrom.balloons.BalloonHandler;
import be.shark_zekrom.command.CommandManager;
import be.shark_zekrom.config.ConfigManager;
import be.shark_zekrom.listener.BalloonsPlusListeners;
import be.shark_zekrom.balloons.BallonService;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Main extends JavaPlugin {

    public static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;

        //Load listeners...
        getServer().getPluginManager().registerEvents(new BalloonsPlusListeners(), this);
        //Load config...
        ConfigManager.getInstance().setPlugin(plugin);
        //Load balloons...
        BalloonHandler.getInstance().loadBalloons();
        //Load commands...
        Objects.requireNonNull(this.getCommand("balloonsplus")).setExecutor(new CommandManager());
        //Load service...
        BallonService.getInstance().startRunnable();
    }

    @Override
    public void onDisable() {
        //Remove all equiped balloons...
        BallonService.getInstance().removeAllBalloon();
    }
}