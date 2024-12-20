package dev.felnull.storagegui.GUI.Item.UniqueItemPage;

import dev.felnull.bettergui.core.GUIItem;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.Data.UniqueItemData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SetItemCMD extends GUIItem {
    public SetItemCMD(InventoryGUI gui, UniqueItemData uniqueItemData) {
        super(gui, new ItemStack(Material.OAK_SIGN));
        setDisplayName(ChatColor.translateAlternateColorCodes('&',"&6CustomModelDataを変更する"));
    }
}
