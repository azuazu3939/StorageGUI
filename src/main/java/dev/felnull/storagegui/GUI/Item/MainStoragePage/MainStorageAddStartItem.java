package dev.felnull.storagegui.GUI.Item.MainStoragePage;

import dev.felnull.bettergui.core.GUIItem;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.GUI.Page.MainStoragePage;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class MainStorageAddStartItem extends GUIItem {
    MainStoragePage mainStoragePage;
    public MainStorageAddStartItem(InventoryGUI gui, MainStoragePage mainStoragePage) {
        super(gui, new ItemStack(Material.SLIME_BALL));
        setDisplayName("Plus1 " + mainStoragePage.invStartPosition);
        this.mainStoragePage = mainStoragePage;
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        mainStoragePage.addSlotStartPosition(1);
    }

    @Override
    public void onShiftClick(InventoryClickEvent e) {
        mainStoragePage.addSlotStartPosition(9);
    }

    @Override
    public void onDropClick(InventoryClickEvent e){
        mainStoragePage.addSlotStartPosition(45);
    }


}
