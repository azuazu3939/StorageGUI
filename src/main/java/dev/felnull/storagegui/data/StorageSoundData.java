package dev.felnull.storagegui.data;

import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.intellij.lang.annotations.Subst;

import java.util.EnumMap;

@SuppressWarnings({"removal", "UnstableApiUsage"})
public class StorageSoundData {

    public Player player;
    public EnumMap<StorageSoundENUM, SoundData> soundENUMSoundMap;

    public StorageSoundData(Player player, EnumMap<StorageSoundENUM, SoundData> soundENUMSoundMap) {
        this.player = player;
        this.soundENUMSoundMap = soundENUMSoundMap;
    }

    public Key getSoundKey(StorageSoundENUM storageSoundENUM) {
        @Subst("custom.sound.example") String soundString = soundENUMSoundMap.get(storageSoundENUM).soundNode;

        if(soundString == null){
            Bukkit.getLogger().warning("指定されたサウンドは無効です:" + storageSoundENUM.toString());
            return Sound.ENTITY_ZOMBIE_AMBIENT.key();
        }

        try {
            Sound sound = Sound.valueOf(soundString);
            return sound.key();
        } catch (IllegalArgumentException e) {
            return Key.key("minecraft:" + soundString);
        }
    }
}
