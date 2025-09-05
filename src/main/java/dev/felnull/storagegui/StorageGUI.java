package dev.felnull.storagegui;

import dev.felnull.storagegui.commands.*;
import dev.felnull.storagegui.config.SoundList;
import dev.felnull.storagegui.config.UniqueItem;
import dev.felnull.storagegui.listener.ChatListener;
import dev.felnull.storagegui.utils.ChatReader;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public final class StorageGUI extends JavaPlugin {

    private static StorageGUI INSTANCE;

    public static StorageGUI getInstance() {
        return INSTANCE;
    }

    public ChatReader getChatReader() {
        return chatReader;
    }

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
        saveDefaultConfig();
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

    public void initListener() {
        Bukkit.getPluginManager().registerEvents(new ChatListener(this), this);
    }

    public void initCommands() {
        Objects.requireNonNull(Bukkit.getPluginCommand("showstoragesoundlist")).setExecutor(new ShowStorageSoundList());
        Objects.requireNonNull(Bukkit.getPluginCommand("soundreload")).setExecutor(new ReloadSound());
        Objects.requireNonNull(Bukkit.getPluginCommand("shownowstoragesound")).setExecutor(new ShowNowSetSound());
        Objects.requireNonNull(Bukkit.getPluginCommand("createuniqueitem")).setExecutor(new CreateUniqueItem());
        Objects.requireNonNull(Bukkit.getPluginCommand("vault")).setExecutor(new Vault());
        Objects.requireNonNull(Bukkit.getPluginCommand("guireload")).setExecutor(new GUIReload(this));
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
