package dev.felnull.storagegui.Utils;

import dev.felnull.BetterStorage;
import dev.felnull.Data.GroupData;
import dev.felnull.Data.InventoryData;
import dev.felnull.Data.StorageData;
import dev.felnull.DataIO.DataIO;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.Data.StorageSoundData;
import dev.felnull.storagegui.Data.StorageSoundENUM;
import dev.felnull.storagegui.GUI.Page.MainStoragePage;
import dev.felnull.storagegui.GUI.Page.ModularStoragePage;
import dev.felnull.storagegui.StorageGUI;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.MessageFormat;
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
        if(StorageGUI.nowOpenInventory.containsKey(invData)){
            gui.openPage(new ModularStoragePage(gui, invData, inventoryNumber, storageData, storageSoundData, StorageGUI.nowOpenInventory.get(invData)));
        }else if(invData.displayName == null) {
            //displayNameがない場合はストレージ名数字
            gui.openPage(new ModularStoragePage(gui, invData, inventoryNumber, storageData, storageSoundData));
        }else {
            gui.openPage(new ModularStoragePage(gui, invData, inventoryNumber, storageData, storageSoundData, invData.displayName));
        }
    }

    //keyで音を流すメソッド
    //
    public static void playStorageSound(Key soundKey, Player player) {
        player.playSound(Sound.sound(soundKey, Sound.Source.BLOCK, 1.0f, 1.0f));
    }

    //MaterialをStringから取得するメソッド
    public static Material getMaterialFromString(String materialName) {
        try {
            return Material.valueOf(materialName.toUpperCase());
        } catch (IllegalArgumentException e) {
            Bukkit.getLogger().warning("[StorageGUI] カスタムアイテムのItem_Typeが不正です");
            Bukkit.getLogger().warning("無効なItem_Type名: " + materialName);
            return null;
        }
    }

    //CS形式(|で改行)の書式でloreを取得
    public static List<Component> splitLoreFactory(String stringLore) {
        List<Component> lore = new ArrayList<>();
        String[] loreParts = stringLore.split("\\|");
        for (String part : loreParts) {
            lore.add(Component.text(ChatColor.translateAlternateColorCodes('&',part)));
        }
        return lore;
    }

    //Stringの中の数字以外の文字を消す=数字だけの文字列にするメソッド
    public static String replaceCharToNumber(String str) {
        return str.replaceAll("[^0-9]", "");
    }

    //Stringを数字にするメソッド
    public static int getIntFromString(String str) {
        return Integer.parseInt(replaceCharToNumber(str));
    }

    //Storageを開くメソッド
    public static boolean openStorageGUI(Player player, GroupData groupData) {
        StorageData storageData = null;
        storageData = groupData.storageData;
        if(storageData == null){
            player.sendMessage("ストレージがありません" + groupData.groupUUID);
            return false;
        }
        InventoryGUI inventoryGUI = new InventoryGUI(player);
        // 必要に応じてページの生成方法・表示内容をここで調整
        inventoryGUI.openPage(new MainStoragePage(inventoryGUI, storageData));
        return true;
    }

    // メッセージをフォーマットして、&で色をつける
    public static String f(String text, Object... args) {
        return MessageFormat.format(ChatColor.translateAlternateColorCodes('&', text), args);
    }

    // 色を消す
    public static String r(String text) {
        return ChatColor.stripColor(text);
    }

    public static Component c(String text){
       return Component.text( f(text));
    }


}
