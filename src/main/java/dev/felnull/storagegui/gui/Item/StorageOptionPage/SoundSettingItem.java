package dev.felnull.storagegui.gui.Item.StorageOptionPage;

import dev.felnull.Data.StorageData;
import dev.felnull.bettergui.core.GUIItem;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.data.StorageSoundData;
import dev.felnull.storagegui.data.StorageSoundENUM;
import dev.felnull.storagegui.gui.Page.Option.SoundOptionPage;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SoundSettingItem extends GUIItem {

    public StorageSoundENUM storageSoundENUM;
    public StorageData storageData;
    public StorageSoundData storageSoundData;

    public SoundSettingItem(InventoryGUI gui, StorageSoundENUM storageSoundENUM, StorageData storageData, StorageSoundData storageSoundData) {
        super(gui, new ItemStack(Material.NOTE_BLOCK));
        this.setDisplayName(storageSoundENUM.name() + "を変更");
        this.storageData = storageData;
        this.storageSoundData = storageSoundData;
        this.storageSoundENUM = storageSoundENUM;
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("現在指定中のサウンド").color(NamedTextColor.AQUA));
        lore.add(Component.text(storageSoundData.soundENUMSoundMap.get(storageSoundENUM).soundName));
        setLore(lore);
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        gui.openPage(new SoundOptionPage(gui, storageSoundENUM, storageData, storageSoundData));
    }
}
