package dev.felnull.storagegui.gui.Item.MainStoragePage;

import dev.felnull.BetterStorage;
import dev.felnull.Data.InventoryData;
import dev.felnull.Data.StorageData;
import dev.felnull.bettergui.core.GUIItem;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.StorageGUI;
import dev.felnull.storagegui.data.StorageSoundData;
import dev.felnull.storagegui.gui.Page.BuyModularStoragePage;
import dev.felnull.storagegui.gui.Page.EditInventoryTags;
import dev.felnull.storagegui.utils.ChatContentType;
import dev.felnull.storagegui.utils.GUIUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ModularStorageItem extends GUIItem {
    InventoryData invData;
    int inventoryNumber;
    StorageData storageData;
    StorageSoundData storageSoundData;

    public ModularStorageItem(InventoryGUI gui, InventoryData invData, int inventoryNumber, StorageData storageData, StorageSoundData storageSoundData) {
        super(gui, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
        if(invData != null){
            super.itemStack = GUIUtils.getCurrentCapacityGlass(invData);
            if(!invData.fullyLoaded){
                try (Connection conn = BetterStorage.BSPlugin.getDatabaseManager().getConnection()) {
                    storageData.loadPage(conn, StorageGUI.pluginName, String.valueOf(inventoryNumber));
                    invData = storageData.storageInventory.get(String.valueOf(inventoryNumber));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        this.invData = invData;
        this.inventoryNumber = inventoryNumber;
        this.storageData = storageData;
        this.storageSoundData = storageSoundData;
        if(invData == null || invData.displayName == null) {
            this.setDisplayName("§6Storage§f : " + inventoryNumber);
        } else {
            this.setDisplayName("§6Storage§f : "+ invData.displayName.replaceAll("&", "§"));
        }
        List<Component> lore = new ArrayList<>();
        if (invData != null && invData.userTags != null && !invData.userTags.isEmpty()) {
            lore.add(GUIUtils.getComponent("§fタグ:"));
            for (String tag : invData.userTags) {
            lore.add(GUIUtils.getComponent("§f- " + tag));
            }
        }
        lore.add(Component.text("§r§b[Qキー]:DisplayName変更"));
        lore.add(Component.text("§r§a[Qキー+Ctrl]:Tag編集"));
        this.setLore(lore);

    }

    @Override
    public void onClick(InventoryClickEvent e) {
        if(invData == null){
            gui.openPage(new BuyModularStoragePage(gui, inventoryNumber, storageData));
            return;
        }
        GUIUtils.openModularInventory(gui, storageData, inventoryNumber, storageSoundData);
    }

    @Override
    public void onDropClick(InventoryClickEvent e) {
        if(invData == null){
            return;
        }
        StorageGUI storageGUI = StorageGUI.getInstance();
        storageGUI.chatReader.registerNextChat(gui.player, ChatContentType.DISPLAY_NAME, invData, storageData, inventoryNumber);

        gui.player.showTitle(Title.title(Component.text("§aチャットに変更したい名前を入力してください"), Component.empty(), Title.Times.times(Duration.ZERO, Duration.ofSeconds(5), Duration.ofSeconds(1))));
        Component message = Component.text("変更したい名前を入力してください").color(TextColor.color(NamedTextColor.WHITE));
        String displayName;
        displayName = Objects.requireNonNullElseGet(invData.displayName, () -> String.valueOf(inventoryNumber));
        Component messageCommand = Component.text("§7[§f現在登録しているDisplayNameを表示する§7]")
                .hoverEvent(HoverEvent.showText(Component.text(displayName)))
                .clickEvent(ClickEvent.suggestCommand(displayName));
        gui.player.sendMessage(message);
        gui.player.sendMessage(messageCommand);
    }

    @Override
    public void onControlDropClick(InventoryClickEvent e) {
        gui.openPage(new EditInventoryTags(gui, invData, storageData, inventoryNumber));
    }
}
