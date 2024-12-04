package dev.felnull.storagegui.Config;

import dev.felnull.storagegui.Data.SoundData;
import dev.felnull.storagegui.Data.StorageSoundData;
import dev.felnull.storagegui.Data.StorageSoundENUM;
import dev.felnull.storagegui.StorageGUI;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class SettingsConfig {
    static File configFolder = StorageGUI.INSTANCE.getDataFolder();
    static File settingsFolder = new File(configFolder, "SettingsData");
    static String soundSection = "Sound.";

    public static void saveSettings(Player player, StorageSoundENUM storageSoundENUM, SoundData soundData) {
        initSaveSettings();
        File settingsFile = new File(settingsFolder, String.valueOf(player.getUniqueId()));
        if(!settingsFile.exists()) {
            try {
                settingsFile.createNewFile(); // 新規ファイルを生成
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileConfiguration settings = YamlConfiguration.loadConfiguration(settingsFile);

        settings.set(soundSection + storageSoundENUM.name(), soundData.soundName);
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

    public static StorageSoundData loadSettings(Player player) {
        File settingsFile = new File(settingsFolder, String.valueOf(player.getUniqueId()));

        FileConfiguration settings = YamlConfiguration.loadConfiguration(settingsFile);
        EnumMap<StorageSoundENUM, SoundData> soundENUMSoundMap = new EnumMap<>(StorageSoundENUM.class);
        Map<String, SoundData> soundNameMap = SoundList.stringSoundDataMap;

        soundENUMSoundMap.put(StorageSoundENUM.STORAGE_OPEN,
                soundNameMap.get(settings.getString(soundSection + StorageSoundENUM.STORAGE_OPEN.name(), "チェストを開く"))
        );

        soundENUMSoundMap.put(StorageSoundENUM.STORAGE_CLOSE,
                soundNameMap.get(settings.getString(soundSection + StorageSoundENUM.STORAGE_CLOSE.name(), "チェストを閉じる"))
        );

        return new StorageSoundData(player, soundENUMSoundMap);
    }


}
