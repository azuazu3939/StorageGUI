package dev.felnull.storagegui.Listener;

import dev.felnull.Data.GroupData;
import dev.felnull.Data.StorageData;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.GUI.Page.MainStoragePage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashSet;
import java.util.Set;

public class CommonListener implements Listener {
    @EventHandler
    public void onInteractChest(PlayerInteractEvent e) {
        e.setCancelled(true);
        if(e.getClickedBlock().getType() == Material.CHEST){
            Set<Player> playerlist = new HashSet<>();
            playerlist.add(e.getPlayer());
            GroupData groupData = new GroupData(e.getPlayer().getUniqueId(),playerlist,"member");

            StorageData storageData = new StorageData(g);

            InventoryGUI inventoryGUI = new InventoryGUI(e.getPlayer());
            inventoryGUI.openPage(new MainStoragePage(inventoryGUI, ));
        }

    }
}
