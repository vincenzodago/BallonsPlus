package be.shark_zekrom.messages;

import be.shark_zekrom.config.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

public class MessageService {
    private static MessageService instance;
    private final Map<String, String> strings;

    private MessageService() {
        if (instance != null)
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        this.strings = new HashMap<>();
    }

    public static MessageService getInstance() {
        if(instance == null)
            instance = new MessageService();
        return instance;
    }

    public void loadMessages() {

        FileConfiguration fileConfiguration = ConfigManager.getInstance().getConfig("Settings.yml");
        ConfigurationSection strings = Objects.requireNonNull(fileConfiguration.getConfigurationSection("Messages"));

        for (String key : strings.getKeys(false)) {
            this.strings.put(key, strings.getString(key));
        }
    }

    public void reload() {
        this.strings.clear();
        loadMessages();
    }

    public String localizeString(String key) {
        return this.strings.containsKey(key) ? this.strings.get(key) : ChatColor.RED + "No translation present for " + key;
    }

}
