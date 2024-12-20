package dev.felnull.storagegui.GUI.Item.UniqueItemPage;

import dev.felnull.bettergui.core.GUIItem;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.Data.UniqueItemData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SetItemDisplayName extends GUIItem {
    public SetItemDisplayName(InventoryGUI gui, UniqueItemData uniqueItemData) {
        super(gui, new ItemStack(Material.NAME_TAG));
        setDisplayName(ChatColor.translateAlternateColorCodes('&',"&6DisplayNameを変更する"));
    }
}
