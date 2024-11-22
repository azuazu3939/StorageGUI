package dev.felnull.storagegui.GUI.Item.BuyModularStorage;

import dev.felnull.Data.GroupPermENUM;
import dev.felnull.Data.InventoryData;
import dev.felnull.Data.StorageData;
import dev.felnull.bettergui.core.GUIItem;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.GUI.Page.MainStoragePage;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

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
