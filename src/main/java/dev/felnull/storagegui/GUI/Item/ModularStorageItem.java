package dev.felnull.storagegui.GUI.Item;

import dev.felnull.Data.InventoryData;
import dev.felnull.bettergui.core.GUIItem;
import dev.felnull.bettergui.core.InventoryGUI;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ModularStorageItem extends GUIItem {

    public ModularStorageItem(InventoryGUI gui, InventoryData invData, int storageNumber) {
        super(gui, new ItemStack(Material.BLACK_STAINED_GLASS));
        if(invData.displayName == null) {
            this.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6Storage&f:") + storageNumber);
        } else {
            this.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6Storage&f:") + ChatColor.translateAlternateColorCodes('&', invData.displayName));
        }
        List<Component> lore = new ArrayList<>();
        for(String tag : invData.userTag){
        lore.add(Component.text(tag));
        }
        this.setLore(lore);
    }

    public void onClick(InventoryClickEvent e) {

    }
}
