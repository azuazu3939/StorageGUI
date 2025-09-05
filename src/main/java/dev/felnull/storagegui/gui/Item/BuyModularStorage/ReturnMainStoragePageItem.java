package dev.felnull.storagegui.gui.Item.BuyModularStorage;

import dev.felnull.Data.StorageData;
import dev.felnull.bettergui.core.GUIItem;
import dev.felnull.bettergui.core.InventoryGUI;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ReturnMainStoragePageItem extends GUIItem {
    public StorageData storageData;

    public ReturnMainStoragePageItem(InventoryGUI gui, StorageData storageData) {
        super(gui, new ItemStack(Material.BARRIER));
        this.storageData = storageData;
        setDisplayName("キャンセルする");
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        gui.currentPage.back();
    }
}
