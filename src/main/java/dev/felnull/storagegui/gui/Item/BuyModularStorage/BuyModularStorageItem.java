package dev.felnull.storagegui.gui.Item.BuyModularStorage;

import dev.felnull.Data.GroupPermENUM;
import dev.felnull.Data.InventoryData;
import dev.felnull.Data.StorageData;
import dev.felnull.bettergui.core.GUIItem;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.StorageGUI;
import dev.felnull.storagegui.commands.GUIReload;
import dev.felnull.storagegui.gui.Page.MainStoragePage;
import dev.felnull.storagegui.utils.InvUtil;
import net.kyori.adventure.text.Component;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class BuyModularStorageItem extends GUIItem {

    public final StorageData storageData;
    public final int inventoryNumber;
    public final int rowCount;

    public BuyModularStorageItem(InventoryGUI gui, StorageData storageData, int inventoryNumber) {
        super(gui, new ItemStack(Material.CHEST));
        this.storageData = storageData;
        this.inventoryNumber = inventoryNumber;
        this.rowCount = 6;
        setDisplayName(GUIReload.getBuyStorage().replaceAll("%value%", String.valueOf(buySlotValue())));
    }

    private int buySlotValue() {
        int pricePerRow = 50;
        return inventoryNumber * rowCount * pricePerRow;
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        Economy economy = StorageGUI.economy;
        Player player = (Player) e.getWhoClicked();

        int basePrice = buySlotValue();

        // 0〜2番のストレージは無料
        boolean isFree = inventoryNumber <= 2;
        int finalPrice = isFree ? 0 : basePrice;

        if (!isFree && !economy.has(player, finalPrice)) {
            double lack = finalPrice - economy.getBalance(player);
            player.sendMessage(Component.text(
                    GUIReload.getBuyFail()
                            .replaceAll("%lack%", String.valueOf(lack))
                            .replaceAll("%value%", String.valueOf(economy.getBalance(player)))
                    )
            );
            return;
        }

        Set<String> perm = new HashSet<>();
        perm.add(GroupPermENUM.MEMBER.getPermName());

        InventoryData invData = new InventoryData(null, rowCount, perm, new HashMap<>());
        storageData.storageInventory.put(String.valueOf(inventoryNumber), invData);
        try {
            InvUtil.saveWithLog(storageData, inventoryNumber);
            if (!isFree) {
                economy.withdrawPlayer(player, finalPrice);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        gui.openPage(new MainStoragePage(gui, storageData));
    }
}
