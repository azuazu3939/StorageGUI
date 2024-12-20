package dev.felnull.storagegui.GUI.Item.UniqueItemPage;

import dev.felnull.bettergui.core.GUIItem;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.Data.UniqueItemData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SetItemLore extends GUIItem {
    public SetItemLore(InventoryGUI gui, UniqueItemData uniqueItemData) {
        super(gui, new ItemStack(Material.GUNPOWDER));
        setDisplayName(ChatColor.translateAlternateColorCodes('&',"&6Loreを変更する(|で改行)"));
    }
}
