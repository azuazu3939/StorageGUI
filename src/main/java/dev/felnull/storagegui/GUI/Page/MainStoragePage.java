package dev.felnull.storagegui.GUI.Page;

import dev.felnull.Data.StorageData;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.GUI.Item.MainStorageAddStartItem;
import dev.felnull.storagegui.GUI.Item.MainStorageSubtractStartItem;
import dev.felnull.storagegui.GUI.Item.ModularStorageItem;
import dev.felnull.storagegui.GUI.StorageGUIPage;
import dev.felnull.storagegui.Utils.GUIUtils;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class MainStoragePage extends StorageGUIPage {

    public final StorageData storageData;
    public List<Integer> numberInventoryList;
    public int invStartPosition;
    public MainStoragePage (InventoryGUI gui, StorageData storageData) {
        super(gui, ChatColor.translateAlternateColorCodes('&', "&6BetterStorage!!!"), 6*9);
        this.storageData = storageData;

        //StorageData内のInventoryDataに紐づいたページ名を取得して数字のページ名のみListに入れている
        List<Integer> numberKeyList = GUIUtils.getNumericKeyList(storageData.storageInventry);
        //昇順にソート
        Collections.sort(numberKeyList);
        this.numberInventoryList = numberKeyList;
        this.invStartPosition = 0;
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
                setItem(slotPosition, new ModularStorageItem(gui, storageData.storageInventry.get(String.valueOf(slot)), slot));
            }else {
                setItem(slotPosition, new ModularStorageItem(gui, null ,slot));
            }
        }
        setItem(0,new MainStorageAddStartItem(gui,this));
        setItem(1,new MainStorageSubtractStartItem(gui,this));
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
        this.setUp();
    }

    public void subtractSlotStartPosition(int minusPosition) {
        this.invStartPosition -= minusPosition;
        if (this.invStartPosition < 0){
            this.invStartPosition = 0;
        }
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
    public void onOutsideWindowRightClick(InventoryClickEvent e) {
        this.addSlotStartPosition(9);
    }

    @Override
    public void onOutsideWindowLeftClick(InventoryClickEvent e) {
        this.subtractSlotStartPosition(9);
    }


}
