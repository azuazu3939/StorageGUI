package dev.felnull.storagegui.GUI.Page.Option;

import dev.felnull.Data.StorageData;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.Config.SettingsConfig;
import dev.felnull.storagegui.Config.SoundList;
import dev.felnull.storagegui.Data.SoundData;
import dev.felnull.storagegui.Data.StorageSoundData;
import dev.felnull.storagegui.Data.StorageSoundENUM;
import dev.felnull.storagegui.GUI.Item.ModularStorageItem;
import dev.felnull.storagegui.GUI.Item.PageBackItem;
import dev.felnull.storagegui.GUI.Item.SoundOptionPage.SoundItem;
import dev.felnull.storagegui.GUI.Item.SoundOptionPage.addPosition;
import dev.felnull.storagegui.GUI.Item.SoundOptionPage.subtractPosition;
import dev.felnull.storagegui.GUI.StorageGUIPage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static dev.felnull.storagegui.Utils.GUIUtils.playStorageSound;

public class SoundOptionPage extends StorageGUIPage {

    public final InventoryGUI gui;
    public final StorageSoundENUM storageSoundENUM;
    public final StorageData storageData;
    public StorageSoundData storageSoundData;
    public int invStartPosition;

    public SoundOptionPage(InventoryGUI gui, StorageSoundENUM storageSoundENUM, StorageData storageData, StorageSoundData storageSoundData) {
        super(gui, storageSoundENUM.name(), 9*6);
        this.gui = gui;
        this.storageSoundENUM = storageSoundENUM;
        this.storageData = storageData;
        this.storageSoundData = storageSoundData;
        this.invStartPosition = 0;
    }

    @Override
    public void setUp() {
        super.inventory.clear();
        this.storageSoundData = SettingsConfig.loadSettings(gui.player);
        Set<SoundData> soundDataSet = SoundList.soundEnumMap.get(storageSoundENUM);
        List<SoundData> soundDataList = new ArrayList<>(soundDataSet);

        for(int slot = 0; slot < 270; slot++) {
            int slotPosition = slot - this.invStartPosition; //インベントリの参照範囲を動かす
            if(slotPosition > 50 || slotPosition < 0){
                continue;
            }
            if(slot < soundDataList.size()) {
                setItem(slotPosition, new SoundItem(gui, soundDataList.get(slot), storageSoundData, storageSoundENUM, this));
            }
        }
        setItem(51, new subtractPosition(gui, this));
        setItem(52, new addPosition(gui, this));
        setItem(53, new PageBackItem(gui, this));
    }

    @Override
    public void back() {
        gui.openPage(new StorageOptionPage(gui, storageData, storageSoundData));
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

    public void addSlotStartPosition(int plusPosition) {
        this.invStartPosition += plusPosition;
        if (this.invStartPosition > 225){
            this.invStartPosition = 225;
        }
        playStorageSound(storageSoundData.getSoundKey(StorageSoundENUM.PAGE_SCROLL), gui.player);
        this.setUp();
    }

    public void subtractSlotStartPosition(int minusPosition) {
        this.invStartPosition -= minusPosition;
        if (this.invStartPosition < 0){
            this.invStartPosition = 0;
        }
        playStorageSound(storageSoundData.getSoundKey(StorageSoundENUM.PAGE_SCROLL), gui.player);
        this.setUp();
    }

    @Override
    public void onOutsideWindowRightClick(InventoryClickEvent e) {
        this.addSlotStartPosition(9);
    }

    @Override
    public void onOutsideWindowLeftClick(InventoryClickEvent e) {
        this.subtractSlotStartPosition(9);
    }
}
