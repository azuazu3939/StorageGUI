package dev.felnull.storagegui.Data;

import java.util.Set;

public class SoundData {
    public final String soundName;
    public final String soundNode;
    public final Set<StorageSoundENUM> storageSoundENUMS;
    public final int customModelData;

    public SoundData(String soundName, String soundNode, Set<StorageSoundENUM> storageSoundENUM, int customModelData) {
        this.soundName = soundName;
        this.soundNode = soundNode;
        this.storageSoundENUMS = storageSoundENUM;
        this.customModelData = customModelData;
    }
}
