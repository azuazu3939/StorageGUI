package dev.felnull.storagegui.gui.Item.MainStoragePage;

import dev.felnull.bettergui.core.GUIItem;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.commands.GUIReload;
import dev.felnull.storagegui.gui.Page.MainStoragePage;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class MainStorageAddStartItem extends GUIItem {
    MainStoragePage mainStoragePage;
    public MainStorageAddStartItem(InventoryGUI gui, @NotNull MainStoragePage mainStoragePage) {
        super(gui, new ItemStack(Material.ARROW));
        setDisplayName(GUIReload.getAddViewPos() + " " + mainStoragePage.invStartPosition);
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
