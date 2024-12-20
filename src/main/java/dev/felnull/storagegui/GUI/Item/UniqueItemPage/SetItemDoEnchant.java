package dev.felnull.storagegui.GUI.Item.UniqueItemPage;

import dev.felnull.bettergui.core.GUIItem;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.Data.UniqueItemData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SetItemDoEnchant extends GUIItem {
    public SetItemDoEnchant(InventoryGUI gui, UniqueItemData uniqueItemData) {
        super(gui, new ItemStack(Material.ENCHANTING_TABLE));
        setDisplayName(ChatColor.translateAlternateColorCodes('&',"&6エンチャントエフェクトの有無を変更する"));
    }
}
