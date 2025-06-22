package dev.felnull.storagegui.GUI.Page;

import dev.felnull.BetterStorage;
import dev.felnull.Data.StorageData;
import dev.felnull.DataIO.DataIO;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.GUI.Item.BuyModularStorage.BuyModularStorageItem;
import dev.felnull.storagegui.GUI.Item.BuyModularStorage.ReturnMainStoragePageItem;
import dev.felnull.storagegui.GUI.StorageGUIPage;
import dev.felnull.storagegui.Listener.CreatePrivateGroupPageClickListener;
import dev.felnull.storagegui.Listener.ModularStoragePageClickListener;
import dev.felnull.storagegui.StorageGUI;
import dev.felnull.storagegui.Utils.GUIUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BuyModularStoragePage extends StorageGUIPage {
    StorageData storageData;
    int inventoryNumber;

    public BuyModularStoragePage(InventoryGUI gui, int inventoryNumber, StorageData storageData) {
        super(gui,  ChatColor.translateAlternateColorCodes('&', "&6新規購入&f-" + inventoryNumber), 3*9);
        this.storageData = storageData;
        this.inventoryNumber = inventoryNumber;
    }
    @Override
    public void setUp() {
        setItem(12,new BuyModularStorageItem(gui, storageData, inventoryNumber));
        setItem(14,new ReturnMainStoragePageItem(gui, storageData));
    }

    @Override
    public void back(){
        gui.openPage(new MainStoragePage(gui,storageData));
    }

    @Override
    public @NotNull Inventory getInventory(){
        return this.inventory;
    }
}
