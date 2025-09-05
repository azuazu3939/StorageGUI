package dev.felnull.storagegui.gui.Page.Option;

import dev.felnull.Data.StorageData;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.config.SoundList;
import dev.felnull.storagegui.data.SoundData;
import dev.felnull.storagegui.data.StorageSoundData;
import dev.felnull.storagegui.data.StorageSoundENUM;
import dev.felnull.storagegui.gui.Item.PageBackItem;
import dev.felnull.storagegui.gui.Item.SoundOptionPage.SoundItem;
import dev.felnull.storagegui.gui.Item.SoundOptionPage.AddPosition;
import dev.felnull.storagegui.gui.Item.SoundOptionPage.SubtractPosition;
import dev.felnull.storagegui.gui.StorageGUIPage;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static dev.felnull.storagegui.utils.GUIUtils.playStorageSound;

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
        Set<SoundData> soundDataSet = SoundList.soundEnumMap.get(storageSoundENUM);
        List<SoundData> soundDataList = new ArrayList<>(soundDataSet);

        for(int slot = 0; slot < 270; slot++) {
            int slotPosition = slot - this.invStartPosition; //インベントリの参照範囲を動かす
            if(slotPosition > 50 || slotPosition < 0){
                continue;
            }
            if(slot < soundDataList.size()) {
                setItem(slotPosition, new SoundItem(gui, soundDataList.get(slot), storageSoundENUM, this));
            }
        }
        setItem(51, new SubtractPosition(gui, this));
        setItem(52, new AddPosition(gui, this));
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
