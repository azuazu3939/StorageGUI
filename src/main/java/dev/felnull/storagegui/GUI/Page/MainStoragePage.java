package dev.felnull.storagegui.GUI.Page;

import dev.felnull.Data.StorageData;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.GUI.StorageGUIPage;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class MainStoragePage extends StorageGUIPage {

    public final StorageData storageData;
    public MainStoragePage (InventoryGUI gui, StorageData storageData) {
        super(gui, ChatColor.translateAlternateColorCodes('&', "&6BetterStorage!!!"), 6*9);
        this.storageData = storageData;
    }

    @Override
    public void setUp() {
    }

    @Override
    public void back() {
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }
}
