package dev.felnull.storagegui.GUI.Item;

import dev.felnull.bettergui.core.GUIItem;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.GUI.Page.MainStoragePage;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class MainStorageSubtractStartItem extends GUIItem {
    MainStoragePage mainStoragePage;
    public MainStorageSubtractStartItem(InventoryGUI gui, MainStoragePage mainStoragePage) {
        super(gui, new ItemStack(Material.SLIME_BALL));
        setDisplayName("minus1 " + mainStoragePage.invStartPosition);
        this.mainStoragePage = mainStoragePage;
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        mainStoragePage.subtractSlotStartPosition(1);
    }

    @Override
    public void onShiftClick(InventoryClickEvent e) {
        mainStoragePage.subtractSlotStartPosition(9);
    }

    @Override
    public void onDropClick(InventoryClickEvent e){
        mainStoragePage.subtractSlotStartPosition(45);
    }

}
