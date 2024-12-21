package dev.felnull.storagegui.GUI.Item.UniqueItemPage;

import dev.felnull.bettergui.core.GUIItem;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.Data.UniqueItemData;
import dev.felnull.storagegui.StorageGUI;
import dev.felnull.storagegui.Utils.ChatContentType;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SetItemID extends GUIItem {
    UniqueItemData uniqueItemData;

    public SetItemID(InventoryGUI gui, UniqueItemData uniqueItemData) {
        super(gui, new ItemStack(Material.GRASS_BLOCK));
        this.uniqueItemData = uniqueItemData;
        setDisplayName(ChatColor.translateAlternateColorCodes('&',"&6ItemIDを設定するまたは指定したItemIDのアイテムを編集する"));
        if(uniqueItemData != null){
            List<Component> loreList = new ArrayList<>();
            loreList.add(Component.text(ChatColor.translateAlternateColorCodes('&', "現在設定されているItemID:" + uniqueItemData.itemID)));
            loreList.add(Component.text(ChatColor.translateAlternateColorCodes('&', "※注意! ItemIDを変更すると今まで存在していたItemが使えなくなります")));
            setLore(loreList);
        }else{
            this.uniqueItemData = new UniqueItemData(null, "", Material.GRASS_BLOCK, 0, "", false);
        }
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        StorageGUI.INSTANCE.getChatReader().registerNextChat(gui.player, ChatContentType.UNIQUEITEM_ITEMID, uniqueItemData);
    }
}
