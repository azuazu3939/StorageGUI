package dev.felnull.storagegui.GUI.Item.SoundOptionPage;

import dev.felnull.bettergui.core.GUIItem;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.Data.SoundData;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SoundItem extends GUIItem {
    public SoundItem(InventoryGUI gui, SoundData soundData) {
        super(gui, new ItemStack(Material.STICK));
        setCMD(soundData.customModelData);
    }
}
