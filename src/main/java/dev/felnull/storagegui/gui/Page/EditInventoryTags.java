package dev.felnull.storagegui.gui.Page;

import dev.felnull.Data.InventoryData;
import dev.felnull.Data.StorageData;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.gui.Item.EditInventoryTagsPage.TagItem;
import dev.felnull.storagegui.gui.StorageGUIPage;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class EditInventoryTags extends StorageGUIPage {

    StorageData storageData;
    InventoryData inventoryData;
    InventoryGUI gui;
    int inventoryNumber;
    int maxTag = 5;//Tagの最大数

    public EditInventoryTags(InventoryGUI gui, InventoryData inventoryData, StorageData storageData, int inventoryNumber) {
        super(gui,  ChatColor.translateAlternateColorCodes('&', "&6タグ編集-" + inventoryData.displayName), 3*9);
        this.storageData = storageData;
        this.inventoryData = inventoryData;
        this.gui = gui;
        this.inventoryNumber = inventoryNumber;
    }
    public void update(InventoryData inventoryData){
        this.inventoryData = inventoryData;
        setUp();
    }
    @Override
    public void setUp() {
        inventory.clear();
        int i = 0;
        for(String tag : inventoryData.getUserTags()){
            setItem(i, new TagItem(gui, tag, inventoryData, storageData, inventoryNumber, this));
            i++;
        }
        if(i <= 9){
            setItem(i, new TagItem(gui, null, inventoryData, storageData, inventoryNumber, this));
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
