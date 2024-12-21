package dev.felnull.storagegui.GUI.Page;

import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.Data.UniqueItemData;
import dev.felnull.storagegui.GUI.Item.UniqueItemPage.*;
import dev.felnull.storagegui.GUI.StorageGUIPage;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class UniqueItemPage extends StorageGUIPage {

    public UniqueItemData uniqueItemData;

    public UniqueItemPage(InventoryGUI gui) {
        super(gui, ChatColor.translateAlternateColorCodes('&', "&6&lUniqueItemFactory"), 3*9);
        this.uniqueItemData = null;
    }
    public UniqueItemPage(InventoryGUI gui, UniqueItemData uniqueItemData) {
        super(gui, ChatColor.translateAlternateColorCodes('&', "&6&lUniqueItemFactory"), 3*9);
        this.uniqueItemData = uniqueItemData;
    }

    @Override
    public void setUp() {
        setItem(10, new SetItemID(gui, uniqueItemData));
        setItem(11, new SetItemType(gui, uniqueItemData));
        setItem(12, new SetItemCMD(gui, uniqueItemData));
        setItem(13, new SetItemDisplayName(gui, uniqueItemData));
        setItem(14, new SetItemLore(gui, uniqueItemData));
        setItem(15, new SetItemDoEnchant(gui, uniqueItemData));
        if(uniqueItemData != null && uniqueItemData.itemID != null && uniqueItemData.material != null){
            setItem(8, new SetItemPreview(gui, uniqueItemData));
        }
    }

    @Override
    public void back() {
        gui.player.closeInventory();
    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }
}
