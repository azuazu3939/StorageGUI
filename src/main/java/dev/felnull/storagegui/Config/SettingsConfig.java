package dev.felnull.storagegui.Config;

import dev.felnull.storagegui.Data.StorageSoundData;
import dev.felnull.storagegui.Data.StorageSoundENUM;
import dev.felnull.storagegui.StorageGUI;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.EnumMap;

public class SettingsConfig {
    static File configFolder = StorageGUI.INSTANCE.getDataFolder();
    static File settingsFolder = new File(configFolder, "SettingsData");
    static String soundSection = "Sound.";

    //CustomSound用
    public static void saveSettings(Player player,StorageSoundENUM storageSoundENUM, String soundName) {
        initSaveSettings();
        File settingsFile = new File(settingsFolder, String.valueOf(player.getUniqueId()));
        FileConfiguration settings = YamlConfiguration.loadConfiguration(settingsFile);

        settings.set(soundSection + storageSoundENUM.name(), soundName);
    }

    //Minecraft純正Sound用
    public static void saveSettings(Player player, StorageSoundENUM storageSoundENUM, Sound soundName) {
        initSaveSettings();
        File settingsFile = new File(settingsFolder, String.valueOf(player.getUniqueId()));
        FileConfiguration settings = YamlConfiguration.loadConfiguration(settingsFile);

        settings.set(soundSection + storageSoundENUM.name(), soundName.toString());
    }

    public static StorageSoundData loadSettings(Player player) {
        File settingsFile = new File(settingsFolder, String.valueOf(player.getUniqueId()));

        FileConfiguration settings = YamlConfiguration.loadConfiguration(settingsFile);
        EnumMap<StorageSoundENUM, String> soundENUMSoundMap = new EnumMap<>(StorageSoundENUM.class);

        soundENUMSoundMap.put(StorageSoundENUM.STORAGE_OPEN, settings.getString(soundSection + StorageSoundENUM.STORAGE_OPEN.name(), Sound.BLOCK_CHEST_OPEN.toString()));
        soundENUMSoundMap.put(StorageSoundENUM.STORAGE_CLOSE, settings.getString(soundSection + StorageSoundENUM.STORAGE_CLOSE.name(), Sound.BLOCK_CHEST_CLOSE.toString()));

        return new StorageSoundData(player, soundENUMSoundMap);
    }

    public static void initSaveSettings() {
        //指定されたフォルダがなかったら生成
        if(!settingsFolder.exists()) {
            if(!settingsFolder.mkdirs()){
                return;
            }
        }
        return;
    }
}
