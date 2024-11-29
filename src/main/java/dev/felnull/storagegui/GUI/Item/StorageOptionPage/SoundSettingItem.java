package dev.felnull.storagegui.GUI.Item.StorageOptionPage;

import dev.felnull.bettergui.core.GUIItem;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.Data.StorageSoundENUM;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SoundSettingItem extends GUIItem {

    public SoundSettingItem(InventoryGUI gui, StorageSoundENUM storageSoundENUM) {
        super(gui, new ItemStack(Material.NOTE_BLOCK));
        this.setDisplayName(storageSoundENUM.name() + "を変更");
    }
}
