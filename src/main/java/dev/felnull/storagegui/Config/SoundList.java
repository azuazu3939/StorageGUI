package dev.felnull.storagegui.Config;

import dev.felnull.storagegui.Data.SoundData;
import dev.felnull.storagegui.Data.StorageSoundENUM;
import dev.felnull.storagegui.StorageGUI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class SoundList {
    static File configFolder = StorageGUI.INSTANCE.getDataFolder();
    static File soundListyaml = new File(configFolder, "SoundList.yml");


    public static void initSoundList() {
        //指定されたフォルダがなかったら生成
        if(!soundListyaml.exists()) {
            try {
                soundListyaml.createNewFile(); // 新規ファイルを生成
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return;
    }

    public static EnumMap<StorageSoundENUM, Set<SoundData>> loadSoundList() {
        FileConfiguration settings = YamlConfiguration.loadConfiguration(soundListyaml);

        // ルートノードのみを取得
        Set<String> rootKeys = settings.getKeys(false);

        EnumMap<StorageSoundENUM, Set<SoundData>> soundDataMap = new EnumMap<>(StorageSoundENUM.class);

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
            // SoundData を作成
            SoundData soundData = new SoundData(
                    key,
                    settings.getString(key + ".SoundNode"),
                    soundENUMS,
                    settings.getInt(key + ".CMD", 0)
            );

            // ENUMごとに振り分け
            for (StorageSoundENUM enumValue : soundENUMS) {
                // 存在しない場合は新しいセットを作成
                soundDataMap.computeIfAbsent(enumValue, k -> new HashSet<>()).add(soundData);
            }

        }

        return soundDataMap;
    }


}

