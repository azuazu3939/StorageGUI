package dev.felnull.storagegui.GUI.Item.UniqueItemPage;

import dev.felnull.bettergui.core.GUIItem;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.Data.UniqueItemData;
import dev.felnull.storagegui.GUI.Page.BuyModularStoragePage;
import dev.felnull.storagegui.StorageGUI;
import dev.felnull.storagegui.Utils.ChatContentType;
import dev.felnull.storagegui.Utils.GUIUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class SetItemType extends GUIItem {
    private UniqueItemData uniqueItemData;
    public SetItemType(InventoryGUI gui, UniqueItemData uniqueItemData) {
        super(gui, new ItemStack(Material.REDSTONE));
        setDisplayName(ChatColor.translateAlternateColorCodes('&',"&6Item_Typeを変更する"));
        this.uniqueItemData = uniqueItemData;
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        if(uniqueItemData == null){
            gui.player.sendMessage("先にItemIDを設定してください");
            return;
        }
        StorageGUI.INSTANCE.getChatReader().registerNextChat(gui.player, ChatContentType.UNIQUEITEM_ITEMTYPE, uniqueItemData);
    }
}
