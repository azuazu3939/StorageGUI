package dev.felnull.storagegui.Utils;

import dev.felnull.Data.InventoryData;
import dev.felnull.Data.StorageData;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.Data.StorageSoundData;
import dev.felnull.storagegui.Data.StorageSoundENUM;
import dev.felnull.storagegui.GUI.Page.ModularStoragePage;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GUIUtils {

    /*
    getNumericListは与えられたMapのキーの中で数字のみを抽出して、
    List<Integer>に入れて返すメソッド
     */
    public static List<Integer> getNumericKeyList(Map<String, InventoryData> map) {
        List<Integer> numericKeys = new ArrayList<>();
        for (String key : map.keySet()) {
            if (key.matches("\\d+")) { // キーが数字のみかチェック
                numericKeys.add(Integer.parseInt(key)); // Integerに変換してリストに追加
            }
        }
        return numericKeys;
    }

    /*
    InventoryData専用
    インベントリの空き容量に応じて
    半分以下なら黄色のガラス空きスロットが1/4ならオレンジのガラス空きスロットがない場合は赤のガラスを返す
     */
    public static ItemStack getCurrentCapacityGlass(InventoryData inventoryData) {
        // インベントリの空きスロット数を取得
        int emptySlots = inventoryData.rows * 9 - inventoryData.itemStackSlot.size();
        if(emptySlots == 0){
            return new ItemStack(Material.RED_STAINED_GLASS_PANE);

        } else if (emptySlots <= inventoryData.rows * 9 / 4) {
            return new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);

        }else if (emptySlots <= inventoryData.rows * 9 / 2) {
            return new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);

        } else  {
            return new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        }
    }

    //毎回ifするのは冗長すぎるので分割
    public static void openModularInventory (InventoryGUI gui, InventoryData invData, int inventoryNumber, StorageData storageData, StorageSoundData storageSoundData){
        playStorageSound(storageSoundData.getSoundKey(StorageSoundENUM.CHANGE_PAGE), gui.player);
        if(invData.displayName == null) {
            //displayNameがない場合はストレージ名数字
            gui.openPage(new ModularStoragePage(gui, invData, inventoryNumber, storageData, storageSoundData));
        }else {
            gui.openPage(new ModularStoragePage(gui, invData, inventoryNumber, storageData, storageSoundData, invData.displayName));
        }
    }

    //keyで音を流すメソッド
    public static void playStorageSound(Key soundKey, Player player) {
        player.playSound(Sound.sound(soundKey, Sound.Source.BLOCK, 1.0f, 1.0f));
    }

    //MaterialをStringから取得するメソッド
    public static Material getMaterialFromString(String materialName) {
        try {
            return Material.valueOf(materialName.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("無効なMaterial名: " + materialName);
            return null;
        }
    }



}
