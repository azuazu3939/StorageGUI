package dev.felnull.storagegui.gui.Page.Option;

import dev.felnull.Data.StorageData;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.config.SoundList;
import dev.felnull.storagegui.data.SoundData;
import dev.felnull.storagegui.data.StorageSoundData;
import dev.felnull.storagegui.data.StorageSoundENUM;
import dev.felnull.storagegui.gui.Item.StorageOptionPage.SoundSettingItem;
import dev.felnull.storagegui.gui.Page.MainStoragePage;
import dev.felnull.storagegui.gui.StorageGUIPage;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.Set;

public class StorageOptionPage extends StorageGUIPage {

    StorageData storageData;
    StorageSoundData storageSoundData;

    public StorageOptionPage(InventoryGUI gui, StorageData storageData, StorageSoundData storageSoundData) {
        super(gui, ChatColor.translateAlternateColorCodes('&', "&6ストレージ:設定"), 6*9);
        this.storageData = storageData;
        this.storageSoundData = storageSoundData;
    }

    @Override
    public void setUp() {
        EnumMap<StorageSoundENUM, Set<SoundData>> soundDataMap = SoundList.soundEnumMap;
        int slot = 0;
        for(StorageSoundENUM storageSoundENUM : StorageSoundENUM.values()){
            setItem(slot, new SoundSettingItem(gui, storageSoundENUM, storageData, storageSoundData));
            slot++;
        }
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
