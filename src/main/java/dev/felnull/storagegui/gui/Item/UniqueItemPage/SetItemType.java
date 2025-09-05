package dev.felnull.storagegui.gui.Item.UniqueItemPage;

import dev.felnull.bettergui.core.GUIItem;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.StorageGUI;
import dev.felnull.storagegui.data.UniqueItemData;
import dev.felnull.storagegui.utils.ChatContentType;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class SetItemType extends GUIItem {

    private final UniqueItemData uniqueItemData;

    public SetItemType(InventoryGUI gui, UniqueItemData uniqueItemData) {
        super(gui, new ItemStack(Material.REDSTONE));
        setDisplayName("§6Item_Typeを変更する");
        this.uniqueItemData = uniqueItemData;
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        if(uniqueItemData == null){
            gui.player.sendMessage("先にItemIDを設定してください");
            return;
        }
        StorageGUI.getInstance().getChatReader().registerNextChat(gui.player, ChatContentType.UNIQUEITEM_ITEMTYPE, uniqueItemData);
    }
}
