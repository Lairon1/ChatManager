package com.lairon.plugin.chatmanager.message;

import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

public enum Message {

    PREFIX, DONT_HAVE_PERMISSION, TIME_MESSAGE, COMMAND_WHITE_LIST;

    private static FileConfiguration config;
    private static File file;
    private static final String MESSAGES_PATH = "Messages";

    public String toMessage(){
        return getPrefix() + ChatColor.translateAlternateColorCodes('&', toMessageRaw());
    }

    public String getPrefix(){
        String message = config.getString(MESSAGES_PATH + "." + PREFIX.name());
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public String toMessageRaw(){
        return config.getString(MESSAGES_PATH + "." + this.name());
    }

    public List<String> toList(){
        return config.getStringList(this.name());
    }

    public static void set(String filePath, Plugin plugin){
        file = new File(filePath);
        if(!file.exists()) plugin.saveResource(file.getName(), true);
        config = YamlConfiguration.loadConfiguration(file);
    }

    public static void reload(Plugin plugin) throws IOException, InvalidConfigurationException {
        if(!file.exists()) plugin.saveResource(file.getName(), true);
        config.load(file);
    }

}
