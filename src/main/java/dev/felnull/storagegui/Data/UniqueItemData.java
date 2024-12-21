package dev.felnull.storagegui.Data;

import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

public class UniqueItemData {

    public String itemID;
    public String displayName;
    public Material material;
    public int cmdNumber;
    public String lore;
    public Boolean doEnchant;

    public UniqueItemData(String itemID, @NotNull String displayName, @NotNull Material material, int cmdNumber, @NotNull String lore, @NotNull Boolean doEnchant) {
        this.itemID = itemID;
        this.displayName = displayName;
        this.material = material;
        this.cmdNumber = cmdNumber;
        this.lore = lore;
        this.doEnchant = doEnchant;
    }
}
