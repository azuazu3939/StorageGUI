package dev.felnull.storagegui.config;

import dev.felnull.storagegui.StorageGUI;
import dev.felnull.storagegui.data.UniqueItemData;
import dev.felnull.storagegui.utils.GUIUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.*;

@SuppressWarnings({"CallToPrintStackTrace", "deprecation"})
public class UniqueItem {
    static File configFolder = StorageGUI.getInstance().getDataFolder();
    static File uniqueItemFolder = new File(configFolder, "UniqueItem");
    static String cmdSection = ".CustomModelData";
    static String displaynameSection = ".DisplayName";
    static String materialNameSection = ".ItemType";
    static String enchantSection = ".Enchant";
    static String loreSection = ".Lore";
    private static Map<String, ItemStack> cachedItems = null;

    public static void saveUniqueItem(UniqueItemData uniqueItemData) {
        initSaveSettings();
        File settingsFile = new File(uniqueItemFolder, uniqueItemData.itemID + ".yml");
        if(!settingsFile.exists()) {
            try {
                settingsFile.createNewFile(); // 新規ファイルを生成
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileConfiguration settings = YamlConfiguration.loadConfiguration(settingsFile);

        //データ追加
        settings.set(uniqueItemData.itemID + displaynameSection, uniqueItemData.displayName);
        settings.set(uniqueItemData.itemID + materialNameSection, uniqueItemData.material.toString());
        settings.set(uniqueItemData.itemID + cmdSection, uniqueItemData.cmdNumber);
        settings.set(uniqueItemData.itemID + loreSection, uniqueItemData.lore);
        settings.set(uniqueItemData.itemID + enchantSection, uniqueItemData.doEnchant);



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

    public static Map<String, ItemStack> loadAllUniqueItems() {
        if (cachedItems != null) return cachedItems; // 初回以降キャッシュ

        Map<String, ItemStack> map = new HashMap<>();
        if (!uniqueItemFolder.exists()) {
            StorageGUI.getInstance().getLogger().warning("UniqueItemフォルダが見つかりません！");
            return map;
        }

        File[] files = uniqueItemFolder.listFiles((dir, name) -> name.endsWith(".yml"));
        if (files == null) return map;

        for (File file : files) {
            YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
            for (String key : yaml.getKeys(false)) {
                ItemStack item = yamlToItemStack(yaml, key);
                if (item != null) {
                    map.put(key, item);
                }
            }
        }

        StorageGUI.getInstance().getLogger().info("カスタムアイテムを読み込みました");
        cachedItems = map;
        return map;
    }

    public static Map<String, ItemStack> loadUniqueItem() {
        List<YamlConfiguration> uniqueItemFileList = new ArrayList<>();
        Map<String, ItemStack> uniqueItemHashMap = new HashMap<>();

        if(uniqueItemFolder.exists() && uniqueItemFolder.isDirectory()){
            // フォルダ内のすべての .yml ファイルを取得
            File[] uniqueItemFiles = uniqueItemFolder.listFiles((dir, name) -> name.endsWith(".yml"));

            if(uniqueItemFiles != null){
                for(File file : uniqueItemFiles){
                    YamlConfiguration uniqueItemYaml = YamlConfiguration.loadConfiguration(file);
                    uniqueItemFileList.add(uniqueItemYaml);
                }

                //読み込んだyamlをMapに登録する
                for(YamlConfiguration yamlFile : uniqueItemFileList){
                    Set<String> keys = yamlFile.getKeys(false);
                    for(String key : keys){
                        uniqueItemHashMap.put(key, yamlToItemStack(yamlFile, key));
                    }
                }

                StorageGUI.getInstance().getLogger().info("カスタムアイテムを読み込みました");
            } else {
                //ymlが存在しない
                StorageGUI.getInstance().getLogger().info("カスタムアイテムが存在しません");
            }

        } else {
            //ディレクトリが存在しない
            StorageGUI.getInstance().getLogger().info("カスタムアイテムが存在しません");
        }
        return uniqueItemHashMap;
    }

    public static ItemStack yamlToItemStack(YamlConfiguration yaml, String key){

        String displayname = yaml.getString(key + displaynameSection);
        String stringMaterial = yaml.getString(key + materialNameSection);
        Integer custommodeldata = (Integer) yaml.get(key + cmdSection);
        String lore = yaml.getString(key + loreSection);
        boolean doEnchant = yaml.getBoolean(key + enchantSection);

        if(stringMaterial == null){
            StorageGUI.getInstance().getLogger().warning("カスタムアイテムのItem_Typeが記載されていません");
            return null;
        }

        Material material = GUIUtils.getMaterialFromString(stringMaterial);
        if(material == null){
            return null;
        }

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if(displayname != null) {
            meta.displayName(Component.text(displayname.replaceAll("&", "§")));
        }
        if(custommodeldata != null){
            meta.setCustomModelData(custommodeldata);
        }
        if(lore != null){
            GUIUtils.splitLoreFactory(lore);
        }
        if(doEnchant){
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
        }

        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack getUniqueItem(String id) {
        Map<String, ItemStack> map = loadAllUniqueItems();
        return map.getOrDefault(id, null);
    }

    public static boolean isSameUniqueItem(ItemStack a, ItemStack b) {
        if (a == null || b == null) return false;
        if (!a.getType().equals(b.getType())) return false;
        return Objects.equals(a.getItemMeta().getCustomModelData(), b.getItemMeta().getCustomModelData());
    }

}
