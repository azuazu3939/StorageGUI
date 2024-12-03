package dev.felnull.storagegui.Config;

import dev.felnull.storagegui.Data.SoundData;
import dev.felnull.storagegui.Data.StorageSoundENUM;
import dev.felnull.storagegui.StorageGUI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class SoundList {
    static File configFolder = StorageGUI.INSTANCE.getDataFolder();
    static File settingsFolder = new File(configFolder, "SoundList.yml");


    public static void initSoundList() {
        //指定されたフォルダがなかったら生成
        if(!settingsFolder.exists()) {
            if(!settingsFolder.mkdirs()){
                return;
            }
        }
        return;
    }

    public static EnumMap<StorageSoundENUM, Set<SoundData>> loadSoundList() {
        FileConfiguration settings = YamlConfiguration.loadConfiguration(settingsFolder);

        // ルートノードのみを取得
        Set<String> rootKeys = settings.getKeys(false);

        EnumMap<StorageSoundENUM, Set<SoundData>> soundDataMap = new EnumMap<>(StorageSoundENUM.class);
        Set<SoundData> soundDataSet = new HashSet<>();

        for(String key : rootKeys){

            Set<StorageSoundENUM> soundENUMS = settings.getStringList(key + ".ENUM").stream()
                    .map(name -> {
                        try {
                            return StorageSoundENUM.valueOf(name);
                        } catch (IllegalArgumentException e) {
                            Bukkit.getLogger().warning("Invalid enum value: " + name);
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            soundDataSet.add(new SoundData(key, settings.getString(key + ".SoundNode"), soundENUMS, settings.getInt(key + ".CMD", 0)));
        }

        return soundDataMap;
    }


}

