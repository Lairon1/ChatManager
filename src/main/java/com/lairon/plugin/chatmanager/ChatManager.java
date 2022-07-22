package com.lairon.plugin.chatmanager;

import com.lairon.plugin.chatmanager.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;

public final class ChatManager extends JavaPlugin implements Listener {

    private ArrayList<String> cmtWhiteList = new ArrayList<>();

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginCommand("chatmanager").setExecutor((sender, command, label, args) -> {

            try {
                Message.reload(this);
            } catch (Throwable e) {
                e.printStackTrace();
                sender.sendMessage("§сПроизошла ошибка при загрузке конфигов (Смотрите в консоль!)");
            }
            cmtWhiteList.clear();
            for (String s : Message.COMMAND_WHITE_LIST.toList()) {
                PluginCommand pluginCommand = Bukkit.getPluginCommand(s);
                if(pluginCommand == null){
                    sender.sendMessage("§сНе найдена команда " + s + " из вайтлиста команд!");
                    continue;
                }
                cmtWhiteList.add(pluginCommand.getName());
                cmtWhiteList.addAll(pluginCommand.getAliases());
            }



            sender.sendMessage("§сКонфиги успешно перезагружены!");
            return false;
        });
        Message.set(getDataFolder() + File.separator + "lang.yml", this);
    }


    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        if(e.getPlayer().getStatistic(Statistic.PLAY_ONE_TICK) >= 18000) return;
        e.setCancelled(true);
        e.getPlayer().sendMessage(Message.TIME_MESSAGE.toMessage());
    }



}
