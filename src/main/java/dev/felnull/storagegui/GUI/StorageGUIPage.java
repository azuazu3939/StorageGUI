package dev.felnull.storagegui.GUI;


import dev.felnull.bettergui.core.GUIPage;
import dev.felnull.bettergui.core.InventoryGUI;

public abstract class StorageGUIPage extends GUIPage {

    public StorageGUIPage(InventoryGUI gui, String name, int size) {
        super(gui, name, size);
    }
}
