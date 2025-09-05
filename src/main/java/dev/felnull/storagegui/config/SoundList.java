package dev.felnull.storagegui.config;

import dev.felnull.storagegui.StorageGUI;
import dev.felnull.storagegui.data.SoundData;
import dev.felnull.storagegui.data.StorageSoundENUM;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings({"UnusedReturnValue", "ResultOfMethodCallIgnored", "CallToPrintStackTrace", "UnstableApiUsage"})
public class SoundList {
    static File configFolder = StorageGUI.getInstance().getDataFolder();
    static File soundListyaml = new File(configFolder, "SoundList.yml");
    public static Map<String, SoundData> stringSoundDataMap = new HashMap<>();
    public static EnumMap<StorageSoundENUM, Set<SoundData>> soundEnumMap;


    public static void initSoundList() {
        //指定されたフォルダがなかったら生成
        if(!soundListyaml.exists()) {
            try {
                soundListyaml.createNewFile(); // 新規ファイルを生成
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static EnumMap<StorageSoundENUM, Set<SoundData>> loadSoundList() {
        FileConfiguration settings = YamlConfiguration.loadConfiguration(soundListyaml);
        //StringとSoundDataの紐づけをいったんクリア
        stringSoundDataMap.clear();

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
                    settings.getString(key + ".SoundNode", "entity.zombie.ambient"),
                    soundENUMS,
                    settings.getInt(key + ".CMD", 0)
            );

            // ENUMごとに振り分け
            for (StorageSoundENUM enumValue : soundENUMS) {
                // 存在しない場合は新しいセットを作成
                soundDataMap.computeIfAbsent(enumValue, k -> new HashSet<>()).add(soundData);
            }
            stringSoundDataMap.put(key,soundData);
        }
        soundEnumMap = soundDataMap;
        return soundDataMap;
    }



}

