package dev.felnull.storagegui.gui.Item.EditInventoryTagsPage;

import dev.felnull.Data.InventoryData;
import dev.felnull.Data.StorageData;
import dev.felnull.DataIO.DataIO;
import dev.felnull.bettergui.core.GUIItem;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.StorageGUI;
import dev.felnull.storagegui.gui.Page.EditInventoryTags;
import dev.felnull.storagegui.utils.ChatContentType;
import dev.felnull.storagegui.utils.GUIUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.title.Title;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.time.Duration;
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
            lore.add(GUIUtils.getComponent("§f[左クリックで追加]"));
            newFlag = true;
        }else {
            lore.add(GUIUtils.getComponent("§f[右クリックで削除]"));
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
        StorageGUI storageGUI = StorageGUI.getInstance();
        storageGUI.chatReader.registerNextChat(gui.player, ChatContentType.TAG, inventoryData, storageData, inventoryNumber);

        gui.player.showTitle(Title.title(Component.text("§aチャットに追加したいTAGを入力してください"), Component.empty(), Title.Times.times(Duration.ZERO, Duration.ofSeconds(5), Duration.ofSeconds(1))));
        Component message = Component.text("§f追加したいTAGを入力してください");
        gui.player.sendMessage(message);
            Component messageCommand = Component.text("§7[§f現在登録しているTagを表示する§7]")
                    .hoverEvent(HoverEvent.showText(Component.text(tagName)))
                    .clickEvent(ClickEvent.suggestCommand(tagName));
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
