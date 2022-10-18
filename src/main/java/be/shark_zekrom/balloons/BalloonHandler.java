package be.shark_zekrom.balloons;

import be.shark_zekrom.config.ConfigManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class BalloonHandler {

    private static BalloonHandler instance;

    private final HashMap<String,Balloon> balloonsList;

    private BalloonHandler() {
        if(instance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
        this.balloonsList = new HashMap<>();
    }

    public static BalloonHandler getInstance() {
        if(instance == null)
            instance = new BalloonHandler();
        return instance;
    }

    public void loadBalloons() {
        this.balloonsList.clear();
        ConfigManager configManager = ConfigManager.getInstance();
        FileConfiguration fileConfiguration = configManager.getConfig("Settings.yml");
        ConfigurationSection configurationSection = fileConfiguration.getConfigurationSection("Balloons");
        if (configurationSection != null) {
            for (String balloonsName : configurationSection.getKeys(false)) {
                boolean isDefault = configurationSection.getBoolean(balloonsName + ".isDefault",true);
                String key = configurationSection.getString(balloonsName + ".key");
                this.balloonsList.put(balloonsName, new Balloon(balloonsName,isDefault,key));
            }
        }
    }

    public Balloon getBalloon(String balloonName) {
        return this.balloonsList.get(balloonName);
    }

    public List<String> getBalloonsName() {
        return this.balloonsList.values().stream().map(Balloon::getName).collect(Collectors.toList());
    }

    public Collection<Balloon> getAllBalloons() {
        return this.balloonsList.values();
    }

    public List<ItemStack> getAllSkulls() {
        return this.balloonsList.values().stream().map(Balloon::getSkull).collect(Collectors.toList());
    }

    public List<ItemStack> getAllowedSkulls(Player player) {
        return this.balloonsList.values().stream()
                .filter(balloon -> balloon.isDefault() || player.hasPermission(balloon.getPermission()))
                .map(Balloon::getSkull).collect(Collectors.toList());
    }

}
