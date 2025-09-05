package dev.felnull.storagegui.uniqueItem;

import dev.felnull.storagegui.StorageGUI;
import dev.felnull.storagegui.utils.GUIUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StorageUnlockItemManager {
    private static final Map<String, Integer> itemPowerMap = new HashMap<>();

    public static void loadConfig() {
        File file = new File(StorageGUI.getInstance().getDataFolder(), "StorageOpenItem.yml");
        if (!file.exists()) return;

        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection section = config.getConfigurationSection("StorageOpenItem");

        if (section == null) return;

        for (String key : section.getKeys(false)) {
            int power = section.getInt(key + ".Power", 0);
            itemPowerMap.put(key, power);
        }
    }

    public static boolean isUnlockItem(ItemStack item) {
        if (item == null || item.getType() == Material.AIR || !item.hasItemMeta()) return false;

        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.hasDisplayName()) return false;

        Component displayName = meta.displayName();
        if (displayName == null) return false;

        String key = PlainTextComponentSerializer.plainText().serialize(displayName);
        return itemPowerMap.containsKey(key);
    }

    public static int getItemPower(ItemStack item) {
        if (!isUnlockItem(item)) return 0;

        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.hasDisplayName()) return 0;

        Component displayName = meta.displayName();
        if (displayName == null) return 0;

        // 表示名から色コードなどを除去したプレーンテキスト取得
        String key = PlainTextComponentSerializer.plainText().serialize(displayName);
        return itemPowerMap.getOrDefault(key, 0);
    }

    public static void addPowerLore(ItemStack item, int remainingPower) {
        if (item == null || !item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();
        List<Component> lore = meta.lore();  // nullの可能性あり

        if (lore == null) {
            lore = new ArrayList<>();
        } else {
            lore = new ArrayList<>(lore); // 不変リスト対策でコピー
        }

        lore.add(GUIUtils.getComponent("&6解放ポイント"));
        lore.add(GUIUtils.getComponent("&e残り: {0} ポイント", remainingPower));

        meta.lore(lore);
        item.setItemMeta(meta);
    }

}
