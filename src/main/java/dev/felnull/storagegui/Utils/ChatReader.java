package dev.felnull.storagegui.Utils;

import dev.felnull.BetterStorage;
import dev.felnull.Data.InventoryData;
import dev.felnull.Data.StorageData;
import dev.felnull.DataIO.DataIO;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.Data.UniqueItemData;
import dev.felnull.storagegui.GUI.Page.EditInventoryTags;
import dev.felnull.storagegui.GUI.Page.MainStoragePage;
import dev.felnull.storagegui.GUI.Page.UniqueItemPage;
import dev.felnull.storagegui.StorageGUI;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;

@RequiredArgsConstructor
public class ChatReader {
    private final StorageGUI plugin;
    private final Map<UUID, ChatContentType> contentTypes = new HashMap<>();
    private final Map<UUID, InventoryData> inventoryDataMap = new HashMap<>();
    private final Map<UUID, StorageData> storageDataMap = new HashMap<>();
    private final Map<UUID, UniqueItemData> uniqueItemDataMap = new HashMap<>();
    private final Map<UUID, Integer> invNumberMap = new HashMap<>();

    //ChatContentを選択してこのメソッドを呼び出すとチャットからデータ(Componentを取得できる)
    public void registerNextChat(Player p, ChatContentType type) {
        contentTypes.put(p.getUniqueId(), type);
        p.closeInventory();
    }
    //StorageのDisplayName設定用
    public void registerNextChat(Player p, ChatContentType type, InventoryData inventoryData, StorageData storageData, int invNumber) {
        contentTypes.put(p.getUniqueId(), type);
        inventoryDataMap.put(p.getUniqueId(), inventoryData);
        storageDataMap.put(p.getUniqueId(), storageData);
        invNumberMap.put(p.getUniqueId(), invNumber);
        p.closeInventory();
    }
    //UniqueItem設定用
    public void registerNextChat(Player p, ChatContentType type, UniqueItemData uniqueItemData) {
        contentTypes.put(p.getUniqueId(), type);
        uniqueItemDataMap.put(p.getUniqueId(), uniqueItemData);
        p.closeInventory();
    }

    public void unregisterNextChat(Player p) {
        contentTypes.remove(p.getUniqueId());
        inventoryDataMap.remove(p.getUniqueId());
        storageDataMap.remove(p.getUniqueId());
        invNumberMap.remove(p.getUniqueId());
    }

    public boolean isRegistered(Player p) {
        return contentTypes.containsKey(p.getUniqueId());
    }

    //
    public void onChat(Player p, Component msg) {
        if (!contentTypes.containsKey(p.getUniqueId())) {
            return;
        }
        ChatContentType type = contentTypes.get(p.getUniqueId());
        UniqueItemData uniqueItemData = uniqueItemDataMap.get(p.getUniqueId());
        InventoryGUI gui = new InventoryGUI(p);
        StorageData storageData = storageDataMap.get(p.getUniqueId());
        InventoryData inventoryData = inventoryDataMap.get(p.getUniqueId());
        int invNumber = invNumberMap.get(p.getUniqueId());
        StorageGUI.INSTANCE.tabCompletionEnabled.remove(p);


        switch (type) {
            //ChatContentTypeがDisplay_Nameの場合の処理
            case DISPLAY_NAME:
                storageData.storageInventory.put(String.valueOf(invNumber), updateInventoryDisplayName(p,componentToString(msg)));
                gui.openPage(new MainStoragePage(gui,storageData));
                break;
            case TAG:
                storageData.storageInventory.put(String.valueOf(invNumber), updateInventoryTags(p, componentToString(msg)));
                gui.openPage(new EditInventoryTags(gui, inventoryData, storageData, invNumber));
                break;
            case UNIQUEITEM_ITEMID:
                uniqueItemData.itemID = componentToString(msg);
                gui.openPage(new UniqueItemPage(gui,uniqueItemData));
                break;
            case UNIQUEITEM_ITEMTYPE:
                uniqueItemData.material = GUIUtils.getMaterialFromString(componentToString(msg));
                gui.openPage(new UniqueItemPage(gui,uniqueItemData));
                break;
            case UNIQUEITEM_CMD:
                uniqueItemData.cmdNumber = GUIUtils.getIntFromString(componentToString(msg));
                gui.openPage(new UniqueItemPage(gui,uniqueItemData));
                break;
            case UNIQUEITEM_DISPLAYNAME:
                uniqueItemData.displayName = componentToString(msg);
                gui.openPage(new UniqueItemPage(gui,uniqueItemData));
                break;
            case UNIQUEITEM_LORE:
                uniqueItemData.lore = componentToString(msg);
                gui.openPage(new UniqueItemPage(gui,uniqueItemData));
                break;
        }



        unregisterNextChat(p);
    }

    //ComponentMessageをStringに変換するメソッド
    public String componentToString(Component msg) {
        return ChatColor.translateAlternateColorCodes('&', LegacyComponentSerializer.legacyAmpersand().serialize(msg));
    }

    //InventoryDataのDisplayNameを更新するメソッド
    public InventoryData updateInventoryDisplayName (Player p, String displayName){
        InventoryData inventoryData = inventoryDataMap.get(p.getUniqueId());
        StorageData storageData = storageDataMap.get(p.getUniqueId());
        if(inventoryData != null){
            inventoryData.displayName = displayName;
        }
        boolean result = DataIO.saveInventoryOnly(storageData.groupData, storageData, String.valueOf(invNumberMap.get(p.getUniqueId())), p.getUniqueId());
        if (!result) {
            p.sendMessage(ChatColor.RED + "インベントリの保存が競合したため失敗しました。もう一度入力してください");
        }
        return inventoryData;
    }

    public InventoryData updateInventoryTags (Player p, String tagName){
        InventoryData inventoryData = inventoryDataMap.get(p.getUniqueId());
        StorageData storageData = storageDataMap.get(p.getUniqueId());
        if(inventoryData != null){
            inventoryData.addUserTag(tagName);
            p.sendMessage("追加");
        }
        boolean result = DataIO.saveTagsOnly(storageData.groupData, String.valueOf(invNumberMap.get(p.getUniqueId())), inventoryData);
        p.sendMessage("さヴぇ");
        if (!result) {
            p.sendMessage(ChatColor.RED + "インベントリの保存が競合したため失敗しました。もう一度入力してください");
        }
        return inventoryData;
    }
}
