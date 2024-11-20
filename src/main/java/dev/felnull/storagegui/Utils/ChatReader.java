package dev.felnull.storagegui.Utils;

import dev.felnull.Data.InventoryData;
import dev.felnull.Data.StorageData;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.GUI.Page.MainStoragePage;
import dev.felnull.storagegui.StorageGUI;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainComponentSerializer;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class ChatReader {
    private final StorageGUI plugin;
    private final Map<UUID, ChatContentType> contentTypes = new HashMap<>();
    private final Map<UUID, InventoryData> inventoryDataMap = new HashMap<>();
    private final Map<UUID, StorageData> storageDataMap = new HashMap<>();

    //ChatContentを選択してこのメソッドを呼び出すとチャットからデータ(Componentを取得できる)
    public void registerNextChat(Player p, ChatContentType type) {
        contentTypes.put(p.getUniqueId(), type);
        p.closeInventory();
    }
    public void registerNextChat(Player p, ChatContentType type, InventoryData inventoryData, StorageData storageData) {
        contentTypes.put(p.getUniqueId(), type);
        inventoryDataMap.put(p.getUniqueId(), inventoryData);
        storageDataMap.put(p.getUniqueId(), storageData);
        p.closeInventory();
    }

    public void unregisterNextChat(Player p) {
        contentTypes.remove(p.getUniqueId());
        inventoryDataMap.remove(p.getUniqueId());
        storageDataMap.remove(p.getUniqueId());
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

        //ChatContentTypeがDisplay_Nameの場合の処理
        if (type == ChatContentType.DISPLAY_NAME) {
            updateInventoryDisplayName(p,componentToString(msg));
            InventoryGUI gui = new InventoryGUI(p);
            StorageData storageData = storageDataMap.get(p.getUniqueId());
            gui.openPage(new MainStoragePage(gui,storageData));
        }

        unregisterNextChat(p);
    }

    //ComponentMessageをStringに変換するメソッド
    public String componentToString(Component msg) {
        return PlainComponentSerializer.plain().serialize(msg);
    }

    //InventoryDataのDisplayNameを更新するメソッド
    public void updateInventoryDisplayName (Player p, String displayName){
        InventoryData inventoryData = inventoryDataMap.get(p.getUniqueId());
        if(inventoryData != null){
            inventoryData.displayName = displayName;
        }
    }
}
