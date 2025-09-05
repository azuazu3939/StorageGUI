package dev.felnull.storagegui.gui.Item.MainStoragePage;

import dev.felnull.bettergui.core.GUIItem;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.commands.GUIReload;
import dev.felnull.storagegui.gui.Page.MainStoragePage;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class MainStorageSubtractStartItem extends GUIItem {
    MainStoragePage mainStoragePage;
    public MainStorageSubtractStartItem(InventoryGUI gui, @NotNull MainStoragePage mainStoragePage) {
        super(gui, new ItemStack(Material.SLIME_BALL));
        setDisplayName(GUIReload.getSubViewPos() + " " + mainStoragePage.invStartPosition);
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
