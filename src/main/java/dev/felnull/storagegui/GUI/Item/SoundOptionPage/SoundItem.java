package dev.felnull.storagegui.GUI.Item.SoundOptionPage;

import dev.felnull.bettergui.core.GUIItem;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.Config.SettingsConfig;
import dev.felnull.storagegui.Data.SoundData;
import dev.felnull.storagegui.Data.StorageSoundData;
import dev.felnull.storagegui.Data.StorageSoundENUM;
import dev.felnull.storagegui.GUI.StorageGUIPage;
import dev.felnull.storagegui.Utils.GUIUtils;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.intellij.lang.annotations.Subst;

import java.util.ArrayList;
import java.util.List;

public class SoundItem extends GUIItem {
    public StorageSoundENUM storageSoundENUM;
    @Subst("sample.sample.sample")
    public SoundData soundData;
    public StorageGUIPage storageGUIPage;

    public SoundItem(InventoryGUI gui, SoundData soundData, StorageSoundData storageSoundData, StorageSoundENUM storageSoundENUM, StorageGUIPage storageGUIPage) {
        super(gui, soundData.displayItem);
        if(storageSoundData.soundENUMSoundMap.get(storageSoundENUM) == soundData){
            ItemMeta meta = this.itemStack.getItemMeta();
            meta.addEnchant(Enchantment.DURABILITY, 1 , true);
            this.itemStack.setItemMeta(meta);
        }
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("[クリック]:").color(NamedTextColor.GRAY).append(Component.text("この音を設定")));
        lore.add(Component.text("[シフトクリック]:").color(NamedTextColor.GRAY).append(Component.text("サンプル再生")));
        setLore(lore);
        this.storageSoundENUM = storageSoundENUM;
        this.soundData = soundData;
        this.storageGUIPage = storageGUIPage;
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        SettingsConfig.saveSettings(gui.player, storageSoundENUM, soundData);
        storageGUIPage.setUp();
    }

    //シフトクリックを押したらサンプル再生
    @Override
    public void onShiftClick(InventoryClickEvent e) {
        GUIUtils.playStorageSound(Key.key(soundData.soundNode), gui.player);
    }
}
