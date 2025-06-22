package dev.felnull.storagegui.GUI.Page;

import dev.felnull.BetterStorage;
import dev.felnull.Data.InventoryData;
import dev.felnull.Data.StorageData;
import dev.felnull.DataIO.DataIO;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.Data.StorageSoundData;
import dev.felnull.storagegui.GUI.StorageGUIPage;
import dev.felnull.storagegui.Listener.ModularStoragePageClickListener;
import dev.felnull.storagegui.StorageGUI;
import dev.felnull.storagegui.Utils.GUIUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ModularStoragePage extends StorageGUIPage {

    Set<Integer> inventoryKeys;
    InventoryData inventoryData;
    int inventoryNumber;
    StorageData storageData;
    ModularStoragePageClickListener listener;
    List<Integer> numberKeyList;
    StorageSoundData storageSoundData;
    ItemStack[] playerInvOld;
    public ItemStack cursorItem = null;

    //インベントリセーブ中のフラグ trueなら更新中
    boolean updating = false;
    Set<Player> nowInvSeenPlayerList;



    //DisplayNameのない場合のコンストラクタ
    public ModularStoragePage (InventoryGUI gui, InventoryData invData, int inventoryNumber, StorageData storageData, StorageSoundData storageSoundData) {
        super(gui, ChatColor.translateAlternateColorCodes('&',"&6Storage&f:" + inventoryNumber), 6*9);
        this.storageSoundData = storageSoundData;
        initPage(invData, inventoryNumber, storageData);
    }

    //DisplayNameのある場合のコンストラクタ
    public ModularStoragePage (InventoryGUI gui, InventoryData invData, int inventoryNumber, StorageData storageData, StorageSoundData storageSoundData, String displayName) {
        super(gui, ChatColor.translateAlternateColorCodes('&',"&6Storage&f:" + displayName), 6*9);
        this.storageSoundData = storageSoundData;
        initPage(invData, inventoryNumber, storageData);
    }

    //既に開かれているInventoryの場合のコンストラクタ
    public ModularStoragePage (InventoryGUI gui, InventoryData invData, int inventoryNumber, StorageData storageData, StorageSoundData storageSoundData, Inventory inv){
        super(gui, inv);
        this.storageSoundData = storageSoundData;
        initPage(invData, inventoryNumber, storageData);
    }

    //コンストラクタの共通処理
    public void initPage(InventoryData invData, int inventoryNumber, StorageData storageData) {
        this.inventoryKeys = invData.itemStackSlot.keySet();
        this.inventoryData = invData;
        this.inventoryNumber = inventoryNumber;
        this.storageData = storageData;
        Inventory playerInv = gui.player.getInventory();
        this.playerInvOld = playerInv.getContents();

        HandlerList.unregisterAll(super.listener); //リスナー無効化
        this.listener = new ModularStoragePageClickListener(this);//このページ専用リスナー起動
        Bukkit.getPluginManager().registerEvents(this.listener, StorageGUI.INSTANCE);

        //StorageData内のInventoryDataに紐づいたページ名を取得して数字のページ名のみListに入れている
        List<Integer> numberKeyList = GUIUtils.getNumericKeyList(storageData.storageInventory);
        //昇順にソート
        Collections.sort(numberKeyList);
        this.numberKeyList = numberKeyList;

        if(StorageGUI.nowInvSeenPlayerListMap.containsKey(invData)){
            this.nowInvSeenPlayerList = StorageGUI.nowInvSeenPlayerListMap.get(invData);
        }else {
            this.nowInvSeenPlayerList = new HashSet<>();
            StorageGUI.nowInvSeenPlayerListMap.put(invData, this.nowInvSeenPlayerList);
        }

    }

    @Override
    public void setUp() {
        if(StorageGUI.nowOpenInventory.containsKey(this.inventoryData)){
            this.nowInvSeenPlayerList.add(gui.player);
        }else {
            StorageGUI.nowOpenInventory.put(this.inventoryData, inventory);
            this.nowInvSeenPlayerList.add(gui.player);
            this.update();
        }
    }

    @Override
    public void update() {
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
            GUIUtils.openModularInventory(gui, storageData.storageInventory.get(String.valueOf(nearinventoryNumber)), nearinventoryNumber, storageData, storageSoundData);
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
            GUIUtils.openModularInventory(gui, storageData.storageInventory.get(String.valueOf(nearinventoryNumber)), nearinventoryNumber, storageData, storageSoundData);
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
        nowInvSeenPlayerList.remove(gui.player);
        if(nowInvSeenPlayerList.isEmpty()){
            StorageGUI.nowOpenInventory.remove(inventoryData);
        }
        inventoryData.saveInventory(this.inventory);

        HandlerList.unregisterAll(this.listener);
        if(!DataIO.saveInventoryOnly(BetterStorage.BSPlugin.getDatabaseManager(), storageData.groupData, String.valueOf(inventoryNumber))) {
            gui.player.getInventory().setContents(playerInvOld);
            gui.player.setItemOnCursor(cursorItem);
            gui.player.sendMessage(GUIUtils.c("&4アイテム更新が競合したため更新前にロールバックしました"));
        }else {
            Bukkit.getLogger().info("[StorageGUI][Save]" + gui.player.getName() + "のストレージ" + inventoryNumber + "を保存しました");
        }
    }
}
