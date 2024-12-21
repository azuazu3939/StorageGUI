package dev.felnull.storagegui.GUI.Item.UniqueItemPage;

import dev.felnull.bettergui.core.GUIItem;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.Data.UniqueItemData;
import dev.felnull.storagegui.StorageGUI;
import dev.felnull.storagegui.Utils.ChatContentType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class SetItemDisplayName extends GUIItem {
    UniqueItemData uniqueItemData;
    public SetItemDisplayName(InventoryGUI gui, UniqueItemData uniqueItemData) {
        super(gui, new ItemStack(Material.NAME_TAG));
        setDisplayName(ChatColor.translateAlternateColorCodes('&',"&6DisplayNameを変更する"));
        this.uniqueItemData = uniqueItemData;
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        if(uniqueItemData == null){
            gui.player.sendMessage("先にItemIDを設定してください");
            return;
        }
        StorageGUI.INSTANCE.getChatReader().registerNextChat(gui.player, ChatContentType.UNIQUEITEM_DISPLAYNAME, uniqueItemData);
    }
}
