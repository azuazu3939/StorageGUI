package dev.felnull.storagegui.config;

import dev.felnull.storagegui.StorageGUI;
import dev.felnull.storagegui.data.SoundData;
import dev.felnull.storagegui.data.StorageSoundData;
import dev.felnull.storagegui.data.StorageSoundENUM;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

@SuppressWarnings("CallToPrintStackTrace")
public class SettingsConfig {
    static File configFolder = StorageGUI.getInstance().getDataFolder();
    static File settingsFolder = new File(configFolder, "SettingsData");
    static String soundSection = "Sound.";

    public static void saveSettings(Player player, StorageSoundENUM storageSoundENUM, SoundData soundData) {
        initSaveSettings();
        File settingsFile = new File(settingsFolder, player.getUniqueId() + ".yml");
        if(!settingsFile.exists()) {
            try {
                settingsFile.createNewFile(); // 新規ファイルを生成
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileConfiguration settings = YamlConfiguration.loadConfiguration(settingsFile);

        //データ追加
        settings.set(soundSection + storageSoundENUM.name(), soundData.soundName);

        //データ書き込み
        try {
            settings.save(settingsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void initSaveSettings() {
        //指定されたフォルダがなかったら生成
        if(!settingsFolder.exists()) {
            settingsFolder.mkdirs();
        }
    }

    public static StorageSoundData loadSettings(Player player) {
        File settingsFile = new File(settingsFolder, player.getUniqueId() + ".yml");

        FileConfiguration settings = YamlConfiguration.loadConfiguration(settingsFile);
        EnumMap<StorageSoundENUM, SoundData> soundENUMSoundMap = new EnumMap<>(StorageSoundENUM.class);
        Map<String, SoundData> soundNameMap = SoundList.stringSoundDataMap;

        soundENUMSoundMap.put(StorageSoundENUM.STORAGE_OPEN,
                soundNameMap.get(settings.getString(soundSection + StorageSoundENUM.STORAGE_OPEN.name(), "チェストを開く"))
        );

        soundENUMSoundMap.put(StorageSoundENUM.STORAGE_CLOSE,
                soundNameMap.get(settings.getString(soundSection + StorageSoundENUM.STORAGE_CLOSE.name(), "チェストを閉じる"))
        );

        soundENUMSoundMap.put(StorageSoundENUM.PAGE_SCROLL,
                soundNameMap.get(settings.getString(soundSection + StorageSoundENUM.PAGE_SCROLL.name(), "ボタンを押す"))
        );

        soundENUMSoundMap.put(StorageSoundENUM.CHANGE_PAGE,
                soundNameMap.get(settings.getString(soundSection + StorageSoundENUM.CHANGE_PAGE.name(), "ボタンを押す"))
        );

        soundENUMSoundMap.put(StorageSoundENUM.BUY,
                soundNameMap.get(settings.getString(soundSection + StorageSoundENUM.BUY.name(), "経験値を獲得"))
        );


        return new StorageSoundData(player, soundENUMSoundMap);
    }


}
