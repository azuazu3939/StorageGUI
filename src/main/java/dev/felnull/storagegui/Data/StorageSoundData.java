package dev.felnull.storagegui.Data;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.EnumMap;
import java.util.Map;

public class StorageSoundData {

    public Player player;
    public EnumMap<StorageSoundENUM, String> soundENUMSoundMap;

    public StorageSoundData(Player player, EnumMap<StorageSoundENUM, String> soundENUMSoundMap) {
        this.player = player;
        this.soundENUMSoundMap = soundENUMSoundMap;
    }
}
