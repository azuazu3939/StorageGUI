package dev.felnull.storagegui;

import dev.felnull.storagegui.Listener.CommonListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class StorageGUI extends JavaPlugin {
    public static StorageGUI INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
        getServer().getPluginManager().registerEvents(new CommonListener(),this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
