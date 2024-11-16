package dev.felnull.storagegui.GUI.Page;

import dev.felnull.Data.StorageData;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.GUI.StorageGUIPage;
import dev.felnull.storagegui.Utils.GUIUtils;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class MainStoragePage extends StorageGUIPage {

    public final StorageData storageData;
    public List<Integer> numberInventoryList;
    public MainStoragePage (InventoryGUI gui, StorageData storageData) {
        super(gui, ChatColor.translateAlternateColorCodes('&', "&6BetterStorage!!!"), 6*9);
        this.storageData = storageData;

        //StorageData内のInventoryDataに紐づいたページ名を取得して数字のページ名のみListに入れている
        List<Integer> numberKeyList = GUIUtils.getNumericKeyList(storageData.storageInventry);
        //昇順にソート
        Collections.sort(numberKeyList);
        this.numberInventoryList = numberKeyList;
    }

    @Override
    public void setUp() {
    }

    @Override
    public void back() {
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }
}
