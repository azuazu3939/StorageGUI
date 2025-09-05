package dev.felnull.storagegui.gui.Item.CreatePrivateGroupPageItem;

import dev.felnull.bettergui.core.GUIItem;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.utils.GUIUtils;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class FalseItem extends GUIItem {
    public FalseItem(InventoryGUI gui) {
        super(gui, new ItemStack(Material.RED_STAINED_GLASS));
        setDisplayName(GUIUtils.textFormat("§6ストレージを作成しない"));
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        e.getWhoClicked().closeInventory();
    }

}
