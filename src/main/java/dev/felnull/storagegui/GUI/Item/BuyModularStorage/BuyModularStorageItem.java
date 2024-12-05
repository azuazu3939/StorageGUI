package dev.felnull.storagegui.GUI.Item.BuyModularStorage;

import dev.felnull.Data.GroupPermENUM;
import dev.felnull.Data.InventoryData;
import dev.felnull.Data.StorageData;
import dev.felnull.bettergui.core.GUIItem;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.GUI.Page.MainStoragePage;
import dev.felnull.storagegui.StorageGUI;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class BuyModularStorageItem extends GUIItem {
    public StorageData storageData;
    public int inventoryNumber;

    public BuyModularStorageItem(InventoryGUI gui, StorageData storageData, int inventoryNumber) {
        super(gui, new ItemStack(Material.CHEST));
        this.storageData = storageData;
        this.inventoryNumber = inventoryNumber;
        setDisplayName(String.valueOf(inventoryNumber * 50) + "$でストレージを購入する!");
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        Economy economy = StorageGUI.economy;
        if(!economy.has((OfflinePlayer) e.getWhoClicked(),inventoryNumber * 50)){
            e.getWhoClicked().sendMessage("お金が"+ String.valueOf((inventoryNumber * 50) - economy.getBalance((OfflinePlayer) e.getWhoClicked())) +"$足りません!" + economy.getBalance((OfflinePlayer) e.getWhoClicked()));
            return;
        }

        Set<String> perm = new HashSet<>();
        perm.add(GroupPermENUM.MEMBER.getPermName());
        storageData.storageInventory.put(String.valueOf(inventoryNumber), new InventoryData(null, null, 6*9, perm, new HashMap<>()));
        economy.withdrawPlayer((OfflinePlayer) e.getWhoClicked(), inventoryNumber * 50);
        gui.openPage(new MainStoragePage(gui,storageData));
    }
}
