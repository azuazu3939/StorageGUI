package dev.felnull.storagegui.Listener;

import dev.felnull.Data.GroupData;
import dev.felnull.Data.InventoryData;
import dev.felnull.Data.StorageData;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.GUI.Page.MainStoragePage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CommonListener implements Listener {
    @EventHandler
    public void onInteractChest(PlayerInteractEvent e) {
        e.setCancelled(true);
        if(e.getClickedBlock().getType() == Material.CHEST){
            Set<Player> playerlist = new HashSet<>();
            playerlist.add(e.getPlayer());
            Map<Player, String[]> playerPermission = new HashMap<>();
            String[] strings = {"member"};
            Set<String> perm = new HashSet<>();
            perm.add("member");
            playerPermission.put(e.getPlayer(), strings);
            GroupData groupData = new GroupData(String.valueOf(e.getPlayer().getUniqueId().hashCode()),playerlist, playerPermission);
            Map<Integer, ItemStack> invitem = new HashMap<>();
            Map<Integer, ItemStack> invitem2 = new HashMap<>();
            invitem.put(1,new ItemStack(Material.DIAMOND));
            for(int i = 0; i <= 26; i++){
                invitem2.put(i,new ItemStack(Material.DIAMOND));
            }
            InventoryData invData = new InventoryData(null, null, 6, perm, invitem);
            InventoryData invData3 = new InventoryData(null, null, 6, perm, invitem);
            InventoryData invData4 = new InventoryData(null, null, 6, perm, invitem);
            InventoryData invData5 = new InventoryData(null, null, 6, perm, invitem);
            InventoryData invData2 = new InventoryData(null, "&6かわいいインベントリ", 6, perm, invitem2);
            Map<String,InventoryData> invmap = new HashMap<>();
            invmap.put(String.valueOf(0),invData);
            invmap.put(String.valueOf(1),invData2);
            invmap.put(String.valueOf(20),invData3);
            invmap.put(String.valueOf(60),invData4);
            invmap.put(String.valueOf(53),invData5);

            StorageData storageData = new StorageData(groupData.groupName, true, perm, invmap, 20);

            InventoryGUI inventoryGUI = new InventoryGUI(e.getPlayer());
            inventoryGUI.openPage(new MainStoragePage(inventoryGUI, storageData));
        }

    }
}
