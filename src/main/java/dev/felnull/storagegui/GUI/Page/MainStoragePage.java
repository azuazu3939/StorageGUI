package dev.felnull.storagegui.GUI.Page;

import dev.felnull.Data.StorageData;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.Config.SettingsConfig;
import dev.felnull.storagegui.Data.StorageSoundData;
import dev.felnull.storagegui.Data.StorageSoundENUM;
import dev.felnull.storagegui.GUI.Item.MainStoragePage.MainStorageAddStartItem;
import dev.felnull.storagegui.GUI.Item.MainStoragePage.MainStorageSubtractStartItem;
import dev.felnull.storagegui.GUI.Item.MainStoragePage.OpenStorageOption;
import dev.felnull.storagegui.GUI.Item.ModularStorageItem;
import dev.felnull.storagegui.GUI.StorageGUIPage;
import dev.felnull.storagegui.Utils.GUIUtils;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import static dev.felnull.storagegui.Utils.GUIUtils.playStorageSound;

public class MainStoragePage extends StorageGUIPage {

    public final StorageData storageData;
    public List<Integer> numberInventoryList;
    public int invStartPosition;
    public StorageSoundData storageSoundData;
    public MainStoragePage (InventoryGUI gui, StorageData storageData) {
        super(gui, ChatColor.translateAlternateColorCodes('&', "&6BetterStorage!!!"), 6*9);
        this.storageData = storageData;

        //StorageData内のInventoryDataに紐づいたページ名を取得して数字のページ名のみListに入れている
        List<Integer> numberKeyList = GUIUtils.getNumericKeyList(storageData.storageInventory);
        //昇順にソート
        Collections.sort(numberKeyList);
        this.numberInventoryList = numberKeyList;
        this.invStartPosition = 0;

        this.storageSoundData = SettingsConfig.loadSettings(gui.player);
        playStorageSound(storageSoundData.getSoundKey(StorageSoundENUM.STORAGE_OPEN), gui.player);

    }

    @Override
    public void setUp() {
        super.inventory.clear();

        for(int slot = 0; slot < 270; slot++) {
            int slotPosition = slot - this.invStartPosition; //インベントリの参照範囲を動かす
            slotPosition += 9; //1行目は空ける
            if(slotPosition > 53 || slotPosition < 9){
                continue;
            }
            if(numberInventoryList.contains(slot)) {
                setItem(slotPosition, new ModularStorageItem(gui, storageData.storageInventory.get(String.valueOf(slot)), slot, storageData, storageSoundData));
            }else {
                setItem(slotPosition, new ModularStorageItem(gui, null , slot, storageData, storageSoundData));
            }
        }
        setItem(0,new MainStorageAddStartItem(gui,this));
        setItem(1,new MainStorageSubtractStartItem(gui,this));
        setItem(2, new OpenStorageOption(gui, storageData, storageSoundData));
    }

    public void changeSlotStartPosition(int startPosition) {
        this.invStartPosition = startPosition;
        if (this.invStartPosition > 225){//9スロット*5行*5ページ
            this.invStartPosition = 225;
        } else if (this.invStartPosition < 0) {
            this.invStartPosition = 0;
        }
        this.setUp();
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
    public void back() {
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

    @Override
    public void close(){
        super.close();
        playStorageSound(storageSoundData.getSoundKey(StorageSoundENUM.STORAGE_CLOSE), gui.player);
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
