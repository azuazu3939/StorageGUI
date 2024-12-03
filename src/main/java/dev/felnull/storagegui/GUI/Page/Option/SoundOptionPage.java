package dev.felnull.storagegui.GUI.Page.Option;

import dev.felnull.Data.StorageData;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.Data.StorageSoundData;
import dev.felnull.storagegui.Data.StorageSoundENUM;
import dev.felnull.storagegui.GUI.StorageGUIPage;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class SoundOptionPage extends StorageGUIPage {
    public final InventoryGUI gui;
    public final StorageSoundENUM storageSoundENUM;
    public final StorageData storageData;
    public StorageSoundData storageSoundData;
    SoundOptionPage(InventoryGUI gui, StorageSoundENUM storageSoundENUM, StorageData storageData, StorageSoundData storageSoundData) {
        super(gui, storageSoundENUM.name(), 9*6);
        this.gui = gui;
        this.storageSoundENUM = storageSoundENUM;
        this.storageData = storageData;
        this.storageSoundData = storageSoundData;
    }

    @Override
    public void setUp() {

    }

    @Override
    public void back() {
        gui.openPage(new StorageOptionPage(gui, storageData, storageSoundData));
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }
}
