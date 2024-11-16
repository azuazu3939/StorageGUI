package dev.felnull.storagegui;

import dev.felnull.storagegui.Listener.CommonListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class StorageGUI extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new CommonListener(),this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
