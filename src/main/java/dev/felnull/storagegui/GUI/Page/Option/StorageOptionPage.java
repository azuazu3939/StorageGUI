package dev.felnull.storagegui.GUI.Page.Option;

import dev.felnull.Data.StorageData;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.Config.SoundList;
import dev.felnull.storagegui.Data.SoundData;
import dev.felnull.storagegui.Data.StorageSoundData;
import dev.felnull.storagegui.Data.StorageSoundENUM;
import dev.felnull.storagegui.GUI.Page.MainStoragePage;
import dev.felnull.storagegui.GUI.StorageGUIPage;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;

public class StorageOptionPage extends StorageGUIPage {

    StorageData storageData;
    StorageSoundData storageSoundData;

    StorageOptionPage(InventoryGUI gui, StorageData storageData, StorageSoundData storageSoundData) {
        super(gui, ChatColor.translateAlternateColorCodes('&', "&6ストレージ:設定"), 6*9);
        this.storageData = storageData;
        this.storageSoundData = storageSoundData;
    }

    @Override
    public void setUp() {
        EnumMap<StorageSoundENUM, SoundData> soundDataMap = SoundList.loadSoundList();
    }

    @Override
    public void back() {
        gui.openPage(new MainStoragePage(gui,storageData));
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }
}
