package dev.felnull.storagegui.GUI.Page;

import dev.felnull.Data.StorageData;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.Config.SettingsConfig;
import dev.felnull.storagegui.Data.StorageSoundData;
import dev.felnull.storagegui.Data.StorageSoundENUM;
import dev.felnull.storagegui.GUI.Item.MainStoragePage.MainStorageAddStartItem;
import dev.felnull.storagegui.GUI.Item.MainStoragePage.MainStorageSubtractStartItem;
import dev.felnull.storagegui.GUI.Item.MainStoragePage.OpenStorageOption;
import dev.felnull.storagegui.GUI.Item.MainStoragePage.ModularStorageItem;
import dev.felnull.storagegui.GUI.StorageGUIPage;
import dev.felnull.storagegui.Utils.GUIUtils;
import org.bukkit.Bukkit;
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
    private static final int MAX_SLOT_COUNT = 270; // 30行 × 9スロット
    private static final int DISPLAY_SLOT_COUNT = 45; // 5行 × 9スロット

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
        /** 表示スロット確認用
        Bukkit.getLogger().info("invStartPosition=" + invStartPosition);
        Bukkit.getLogger().info("表示予定スロット: " +
                IntStream.range(0, 45)
                        .map(i -> invStartPosition + i)
                        .mapToObj(String::valueOf)
                        .collect(Collectors.joining(", "))
        );
         **/

        int displaySlots = 45; // 6行中、下5行＝5*9スロット（0-8はボタンなど用）
        for (int i = 0; i < displaySlots; i++) {
            int slot = invStartPosition + i;
            int uiSlot = i + 9; // UIの表示位置（1行目は固定）

            if (uiSlot > 53) {
                break; // GUIの最大スロット数超え防止
            }

            if (numberInventoryList.contains(slot)) {
                if(storageData.storageInventory.get(String.valueOf(slot)) == null){
                    Bukkit.getLogger().info("mainstorage:storageData.storageInventory.get = null");
                }else {
                    Bukkit.getLogger().info("mainstorage:storageData.storageInventory.get = true");
                }

                setItem(uiSlot, new ModularStorageItem(gui,
                        storageData.storageInventory.get(String.valueOf(slot)),
                        slot,
                        storageData,
                        storageSoundData));
            } else {
                setItem(uiSlot, new ModularStorageItem(gui, null, slot, storageData, storageSoundData));
            }
        }

        // UI固定ボタン
        setItem(0, new MainStorageAddStartItem(gui, this));
        setItem(1, new MainStorageSubtractStartItem(gui, this));
        setItem(2, new OpenStorageOption(gui, storageData, storageSoundData));
    }

    public void changeSlotStartPosition(int startPosition) {
        this.invStartPosition = startPosition;

        int maxStart = MAX_SLOT_COUNT - DISPLAY_SLOT_COUNT;
        if (this.invStartPosition > maxStart) {
            this.invStartPosition = maxStart;
        } else if (this.invStartPosition < 0) {
            this.invStartPosition = 0;
        }

        this.setUp();
    }

    public void addSlotStartPosition(int plusPosition) {
        this.invStartPosition += plusPosition;

        int maxStart = MAX_SLOT_COUNT - DISPLAY_SLOT_COUNT;
        if (this.invStartPosition > maxStart) {
            this.invStartPosition = maxStart;
        }

        playStorageSound(storageSoundData.getSoundKey(StorageSoundENUM.PAGE_SCROLL), gui.player);
        this.setUp();
    }


    public void subtractSlotStartPosition(int minusPosition) {
        this.invStartPosition -= minusPosition;
        if (this.invStartPosition < 0) {
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
