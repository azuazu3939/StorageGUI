package dev.felnull.storagegui.GUI.Page;

import dev.felnull.Data.InventoryData;
import dev.felnull.Data.StorageData;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.GUI.StorageGUIPage;
import dev.felnull.storagegui.Listener.ModularStoragePageClickListener;
import dev.felnull.storagegui.StorageGUI;
import dev.felnull.storagegui.Utils.GUIUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class ModularStoragePage extends StorageGUIPage {

    Set<Integer> inventoryKeys;
    InventoryData inventoryData;
    int inventoryNumber;
    StorageData storageData;
    ModularStoragePageClickListener listener;
    List<Integer> numberKeyList;

    //インベントリセーブ中のフラグ trueなら更新中
    boolean updating = false;

    //DisplayNameのない場合のコンストラクタ
    public ModularStoragePage (InventoryGUI gui, InventoryData invData, int inventoryNumber, StorageData storageData) {
        super(gui, ChatColor.translateAlternateColorCodes('&',"&6Storage&f:" + inventoryNumber), 6*9);
        initPage(invData, inventoryNumber, storageData);
    }

    //DisplayNameのある場合のコンストラクタ
    public ModularStoragePage (InventoryGUI gui, InventoryData invData, int inventoryNumber, StorageData storageData, String displayName) {
        super(gui, ChatColor.translateAlternateColorCodes('&',"&6Storage&f:" + displayName), 6*9);
        initPage(invData, inventoryNumber, storageData);
    }

    //コンストラクタの共通処理
    public void initPage(InventoryData invData, int inventoryNumber, StorageData storageData) {
        this.inventoryKeys = invData.itemStackSlot.keySet();
        this.inventoryData = invData;
        this.inventoryNumber = inventoryNumber;
        this.storageData = storageData;
        HandlerList.unregisterAll(super.listener); //リスナー無効化
        this.listener = new ModularStoragePageClickListener(this);//このページ専用リスナー起動
        Bukkit.getPluginManager().registerEvents(this.listener, StorageGUI.INSTANCE);

        //StorageData内のInventoryDataに紐づいたページ名を取得して数字のページ名のみListに入れている
        List<Integer> numberKeyList = GUIUtils.getNumericKeyList(storageData.storageInventry);
        //昇順にソート
        Collections.sort(numberKeyList);
        this.numberKeyList = numberKeyList;

    }

    @Override
    public void setUp() {
        for(int slot : inventoryKeys){
            this.setItem(slot, inventoryData.itemStackSlot.get(slot));
        }
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
        //インベントリ更新中はreturn
        if(updating){
            return;
        }

        //ストレージ上限0-225
        if(inventoryNumber >= 0 && inventoryNumber < 225){
            Integer nearinventoryNumber = nearFigure(numberKeyList, inventoryNumber,false);
            if(nearinventoryNumber == null){
                return;
            }
            GUIUtils.openModularInventory(gui, storageData.storageInventry.get(String.valueOf(nearinventoryNumber)), nearinventoryNumber, storageData);
        }

    }

    @Override
    public void onOutsideWindowLeftClick(InventoryClickEvent e) {
        //インベントリ更新中はreturn
        if(updating){
            return;
        }

        //ストレージ上限0-225 このデータStrageDataで保持すべきか。。？
        if(inventoryNumber >= 0 && inventoryNumber < 225){
            Integer nearinventoryNumber = nearFigure(numberKeyList, inventoryNumber,true);
            if(nearinventoryNumber == null){
                return;
            }
            GUIUtils.openModularInventory(gui, storageData.storageInventry.get(String.valueOf(nearinventoryNumber)), nearinventoryNumber, storageData);
        }
    }

    //解放済みでかつ一番近いインベントリを取得する　マイナス方向の場合はnegative:true
    public Integer nearFigure(List<Integer> numberKeyList , int target, boolean negative) {
        int targetPosition = numberKeyList.indexOf(target);
        if(targetPosition == -1){
            return null;
        }

        // 取得するインデックスを決定(+方向(右)か-方向か(左))
        int newPosition = negative ? targetPosition - 1 : targetPosition + 1;

        if (newPosition >= 0 && newPosition < numberKeyList.size()) {
            return numberKeyList.get(newPosition);
        } else {
            return null;
        }


    }

    @Override
    public void close() {
        this.updating = true;
        this.updating = inventoryData.saveInventory(this.inventory);
        HandlerList.unregisterAll(this.listener);
    }
}
