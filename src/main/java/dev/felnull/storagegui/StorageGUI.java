package dev.felnull.storagegui;

import dev.felnull.storagegui.Config.SoundList;
import dev.felnull.storagegui.Listener.ChatListener;
import dev.felnull.storagegui.Listener.CommonListener;
import dev.felnull.storagegui.Utils.ChatReader;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class StorageGUI extends JavaPlugin {

    public static StorageGUI INSTANCE;
    @Getter
    public ChatReader chatReader;
    public static Economy economy = null;

    @Override
    public void onEnable() {
        INSTANCE = this;
        initListener();
        this.chatReader = new ChatReader(this);
        if (!setupEconomy() ) {
            getLogger().warning("Vaultが見つからないか、Economyサービスが利用できません。StorageGUIを強制終了します");
            getServer().getPluginManager().disablePlugin(this);
        }
        SoundList.initSoundList();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void initListener() {
        Bukkit.getPluginManager().registerEvents(new CommonListener(),this);
        Bukkit.getPluginManager().registerEvents(new ChatListener(this), this);
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return true;
    }

}
