package dev.felnull.storagegui.GUI.Item.CreatePrivateGroupPageItem;

import dev.felnull.BetterStorage;
import dev.felnull.Data.GroupData;
import dev.felnull.Data.StorageData;
import dev.felnull.DataIO.DataIO;
import dev.felnull.bettergui.core.GUIItem;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.StorageGUI;
import dev.felnull.storagegui.Utils.GUIUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashSet;

public class FalseItem extends GUIItem {
    public FalseItem(InventoryGUI gui) {
        super(gui, new ItemStack(Material.RED_STAINED_GLASS));
        setDisplayName(GUIUtils.f("&6ストレージを作成しない"));
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        e.getWhoClicked().closeInventory();
    }

}
