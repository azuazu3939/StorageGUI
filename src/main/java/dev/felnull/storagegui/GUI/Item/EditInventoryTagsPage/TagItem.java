package dev.felnull.storagegui.GUI.Item.EditInventoryTagsPage;

import dev.felnull.Data.InventoryData;
import dev.felnull.Data.StorageData;
import dev.felnull.DataIO.DataIO;
import dev.felnull.bettergui.core.GUIItem;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.GUI.Page.EditInventoryTags;
import dev.felnull.storagegui.StorageGUI;
import dev.felnull.storagegui.Utils.ChatContentType;
import dev.felnull.storagegui.Utils.GUIUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class TagItem extends GUIItem {

    String tagName;
    InventoryData inventoryData;
    StorageData storageData;
    int inventoryNumber;
    boolean newFlag = false;
    EditInventoryTags page;

    public TagItem(InventoryGUI gui, String tagName, InventoryData inventoryData, StorageData storageData, int inventoryNumber, EditInventoryTags page) {
        super(gui, new ItemStack(Material.NAME_TAG));
        List<Component> lore = new ArrayList<>();
        if(tagName == null){
            tagName = "新しく追加する";
            lore.add(GUIUtils.c("&f[左クリックで追加]"));
            newFlag = true;
        }else {
            lore.add(GUIUtils.c("&f[右クリックで削除]"));
        }
        setDisplayName(tagName);
        this.tagName = tagName;
        this.inventoryData = inventoryData;
        this.storageData = storageData;
        this.inventoryNumber = inventoryNumber;
        this.page = page;

        setLore(lore);
    }

    @Override
    public void onLeftClick(InventoryClickEvent e) {
        if(newFlag){
        if(inventoryData == null){
            return;
        }
        StorageGUI storageGUI = StorageGUI.INSTANCE;
        storageGUI.chatReader.registerNextChat(gui.player, ChatContentType.TAG, inventoryData, storageData, inventoryNumber);

        gui.player.sendTitle(ChatColor.translateAlternateColorCodes('&',"&aチャットに追加したいTAGを入力してください"), "", 0, 100, 20);
        Component message = Component.text("追加したいTAGを入力してください").color(TextColor.color(NamedTextColor.WHITE));
        gui.player.sendMessage(message);
            Component messageCommand = Component.text(ChatColor.translateAlternateColorCodes('&',"&7[&f現在登録しているTagを表示する&7]"))
                    .hoverEvent(HoverEvent.showText(Component.text(tagName)))
                    .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.SUGGEST_COMMAND, tagName));
            gui.player.sendMessage(messageCommand);
        }
    }

    @Override
    public void onRightClick(InventoryClickEvent e) {
        if(!newFlag){
            if (inventoryData == null) {
                return;
            }
            if (tagName.equals("新しく追加する")) {
                return;
            }

            inventoryData.removeUserTag(tagName);
            DataIO.saveTagsOnly(storageData.groupData, String.valueOf(inventoryNumber), inventoryData);
            page.update(inventoryData);
        }
    }
}
