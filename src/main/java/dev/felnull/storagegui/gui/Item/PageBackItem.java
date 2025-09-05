package dev.felnull.storagegui.gui.Item;

import dev.felnull.bettergui.core.GUIItem;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.commands.GUIReload;
import dev.felnull.storagegui.gui.StorageGUIPage;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class PageBackItem extends GUIItem {

    public StorageGUIPage storageGUIPage;

    public PageBackItem(InventoryGUI gui, StorageGUIPage storageGUIPage) {
        super(gui, new ItemStack(Material.BARRIER));
        setDisplayName(GUIReload.getPageBack());
        this.storageGUIPage = storageGUIPage;
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        storageGUIPage.back();
    }

}
