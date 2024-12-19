package dev.felnull.storagegui.Config;

import dev.felnull.storagegui.StorageGUI;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UniqueItem {
    static File configFolder = StorageGUI.INSTANCE.getDataFolder();
    static File uniqueItemFolder = new File(configFolder, "UniqueItem");
    static String cmdSection = "CustomModelData";
    static String displaynameSection = "DisplayName";
    static String materialNameSection = "ItemType";
    static String enchantSection = "Enchant";
    static String loreSection = "Lore";

    public static void saveUniqueItem(String itemID, String displayName, Material material, int cmdNumber, String lore, boolean doEnchant) {
        initSaveSettings();
        File settingsFile = new File(uniqueItemFolder, itemID + ".yml");
        if(!settingsFile.exists()) {
            try {
                settingsFile.createNewFile(); // 新規ファイルを生成
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileConfiguration settings = YamlConfiguration.loadConfiguration(settingsFile);

        //データ追加
        settings.set(displaynameSection, displayName);
        settings.set(materialNameSection, material.toString());
        settings.set(cmdSection, cmdNumber);
        settings.set(loreSection, lore);
        settings.set(enchantSection, doEnchant);



        //データ書き込み
        try {
            settings.save(settingsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void initSaveSettings() {
        //指定されたフォルダがなかったら生成
        if(!uniqueItemFolder.exists()) {
            if(!uniqueItemFolder.mkdirs()){
                return;
            }
        }
        return;
    }

    public static Map<String, ItemStack> loadUniqueItem() {
        File settingsFile = new File(uniqueItemFolder, "近いうちにちゃんと書く" + ".yml");

        FileConfiguration settings = YamlConfiguration.loadConfiguration(settingsFile);
        HashMap<String, ItemStack> uniqueItemHashMap;

        



        return null;
    }


}
