package dev.felnull.storagegui.gui.Item.UniqueItemPage;

import dev.felnull.bettergui.core.GUIItem;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.data.UniqueItemData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class SetItemDoEnchant extends GUIItem {
    UniqueItemData uniqueItemData;
    public SetItemDoEnchant(InventoryGUI gui, UniqueItemData uniqueItemData) {
        super(gui, new ItemStack(Material.ENCHANTING_TABLE));
        setDisplayName(ChatColor.translateAlternateColorCodes('&',"&6エンチャントエフェクトの有無を変更する"));
        this.uniqueItemData = uniqueItemData;
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        if(uniqueItemData == null){
            gui.player.sendMessage("先にItemIDを設定してください");
            return;
        }
        uniqueItemData.doEnchant = !uniqueItemData.doEnchant;
        gui.currentPage.setUp();
    }
}
