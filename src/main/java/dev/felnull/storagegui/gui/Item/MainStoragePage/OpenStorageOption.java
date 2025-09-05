package dev.felnull.storagegui.gui.Item.MainStoragePage;

import dev.felnull.Data.StorageData;
import dev.felnull.bettergui.core.GUIItem;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.data.StorageSoundData;
import dev.felnull.storagegui.gui.Page.Option.StorageOptionPage;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class OpenStorageOption extends GUIItem {

    public StorageData storageData;
    public StorageSoundData storageSoundData;

    public OpenStorageOption(InventoryGUI gui, StorageData storageData, StorageSoundData storageSoundData) {
        super(gui, new ItemStack(Material.STONE_BUTTON));
        this.storageData = storageData;
        this.storageSoundData = storageSoundData;
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        gui.openPage(new StorageOptionPage(gui, storageData, storageSoundData));
    }
}
