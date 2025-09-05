package dev.felnull.storagegui;

import dev.felnull.Data.InventoryData;
import dev.felnull.storagegui.Commands.CreateUniqueItem;
import dev.felnull.storagegui.Commands.ReloadSound;
import dev.felnull.storagegui.Commands.ShowNowSetSound;
import dev.felnull.storagegui.Commands.ShowStorageSoundList;
import dev.felnull.storagegui.Config.SoundList;
import dev.felnull.storagegui.Config.UniqueItem;
import dev.felnull.storagegui.Listener.ChatListener;
import dev.felnull.storagegui.Listener.CommonListener;
import dev.felnull.storagegui.Listener.TabCompleteListener;
import dev.felnull.storagegui.Utils.ChatReader;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class StorageGUI extends JavaPlugin {

    public static StorageGUI INSTANCE;
    @Getter
    public ChatReader chatReader;
    public static Economy economy = null;
    public static String pluginName = "StorageGUI";

    //UniqueItemのMap
    public static Map<String, ItemStack> uniqueItemHashMap;
    //Material名のTab補完の管理用map
    public final ConcurrentHashMap<Player, Boolean> tabCompletionEnabled = new ConcurrentHashMap<>();

    @Override
    public void onEnable() {
        INSTANCE = this;
        initListener();//リスナー登録
        initCommands();//コマンド登録
        this.chatReader = new ChatReader(this);
        //Soundを初期化
        SoundList.loadSoundList();

        if (!setupEconomy() ) {
            getLogger().warning("Vaultが見つからないか、Economyサービスが利用できません。StorageGUIを強制終了します");
            getServer().getPluginManager().disablePlugin(this);
        }
        saveResourceFile("SoundList.yml");
        SoundList.initSoundList();
        uniqueItemHashMap = UniqueItem.loadUniqueItem();
        // ProtocolLibを使用してTAB補完を処理
        //new TabCompleteListener();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void initListener() {
        Bukkit.getPluginManager().registerEvents(new CommonListener(),this);
        Bukkit.getPluginManager().registerEvents(new ChatListener(this), this);
    }

    public void initCommands() {
        Bukkit.getPluginCommand("showstoragesoundlist").setExecutor(new ShowStorageSoundList());
        Bukkit.getPluginCommand("soundreload").setExecutor(new ReloadSound());
        Bukkit.getPluginCommand("shownowstoragesound").setExecutor(new ShowNowSetSound());
        Bukkit.getPluginCommand("createuniqueitem").setExecutor(new CreateUniqueItem());
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

    public void saveResourceFile(String fileName) {
        File file = new File(getDataFolder(), fileName);
        if (!file.exists()) {
            saveResource(fileName, false); // リソースをコピー（falseで上書きしない）
            getLogger().info(fileName + " を生成しました");
        } else {
            getLogger().info(fileName + " はすでに存在するため生成しませんでした");
        }
    }

    public boolean isTabCompletionEnabled(Player player) {
        return tabCompletionEnabled.getOrDefault(player, false);
    }


}
