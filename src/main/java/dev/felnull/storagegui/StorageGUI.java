package dev.felnull.storagegui;

import dev.felnull.storagegui.Listener.ChatListener;
import dev.felnull.storagegui.Listener.CommonListener;
import dev.felnull.storagegui.Utils.ChatReader;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class StorageGUI extends JavaPlugin {

    public static StorageGUI INSTANCE;
    @Getter
    public ChatReader chatReader;

    @Override
    public void onEnable() {
        INSTANCE = this;
        initListener();
        this.chatReader = new ChatReader(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void initListener() {
        Bukkit.getPluginManager().registerEvents(new CommonListener(),this);
        Bukkit.getPluginManager().registerEvents(new ChatListener(this), this);
    }

}
