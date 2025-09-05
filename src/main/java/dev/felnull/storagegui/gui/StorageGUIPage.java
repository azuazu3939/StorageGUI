package dev.felnull.storagegui.gui;


import dev.felnull.bettergui.core.GUIPage;
import dev.felnull.bettergui.core.InventoryGUI;
import org.bukkit.inventory.Inventory;

public abstract class StorageGUIPage extends GUIPage {

    public StorageGUIPage(InventoryGUI gui, String name, int size) {
        super(gui, name, size);
    }

    public StorageGUIPage(InventoryGUI gui, Inventory inv) {
        super(gui, inv);
    }
}
