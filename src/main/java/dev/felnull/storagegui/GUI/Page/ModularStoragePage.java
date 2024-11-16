package dev.felnull.storagegui.GUI.Page;

import dev.felnull.Data.InventoryData;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.GUI.StorageGUIPage;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class ModularStoragePage extends StorageGUIPage {

    Set<Integer> inventoryKeys;
    InventoryData inventoryData;
    public ModularStoragePage (InventoryGUI gui, InventoryData invData,int storageNumber) {
        super(gui, ChatColor.translateAlternateColorCodes('&',"&6Storage&f: " + storageNumber), 6*9);
        this.inventoryKeys = invData.itemStackSlot.keySet();
        this.inventoryData = invData;
    }

    @Override
    public void setUp() {
        for(int slot : inventoryKeys){
            this.setItem(slot, inventoryData.itemStackSlot.get(slot));
        }
    }

    @Override
    public void back() {
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }
}
