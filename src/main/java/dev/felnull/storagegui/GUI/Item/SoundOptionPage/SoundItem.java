package dev.felnull.storagegui.GUI.Item.SoundOptionPage;

import dev.felnull.bettergui.core.GUIItem;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.Config.SettingsConfig;
import dev.felnull.storagegui.Data.SoundData;
import dev.felnull.storagegui.Data.StorageSoundData;
import dev.felnull.storagegui.Data.StorageSoundENUM;
import dev.felnull.storagegui.GUI.StorageGUIPage;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SoundItem extends GUIItem {
    public StorageSoundENUM storageSoundENUM;
    public SoundData soundData;
    public StorageGUIPage storageGUIPage;

    public SoundItem(InventoryGUI gui, SoundData soundData, StorageSoundData storageSoundData, StorageSoundENUM storageSoundENUM, StorageGUIPage storageGUIPage) {
        super(gui, soundData.displayItem);
        if(storageSoundData.soundENUMSoundMap.get(storageSoundENUM) == soundData){
            ItemMeta meta = this.itemStack.getItemMeta();
            meta.addEnchant(Enchantment.DURABILITY, 1 , true);
            this.itemStack.setItemMeta(meta);
        }
        this.storageSoundENUM = storageSoundENUM;
        this.soundData = soundData;
        this.storageGUIPage = storageGUIPage;
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        SettingsConfig.saveSettings(gui.player, storageSoundENUM, soundData);
        storageGUIPage.setUp();
    }
}
