package dev.felnull.storagegui.listener;

import org.bukkit.event.Listener;

public class CommonListener implements Listener {
    /**
    @EventHandler
    public void onInteractChest(PlayerInteractEvent e) {
        e.setCancelled(true);
        if(e.getClickedBlock().getType() == Material.CHEST){
            Set<Player> playerList = new HashSet<>();
            playerList.add(e.getPlayer());
            Map<Player, String[]> playerPermission = new HashMap<>();
            String[] strings = {"member"};
            Set<String> perm = new HashSet<>();
            perm.add("member");
            playerPermission.put(e.getPlayer(), strings);

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

            StorageData storageData = new StorageData(perm, invmap, 20);
            GroupData groupData = new GroupData(Bukkit.getServer().getOfflinePlayer(e.getPlayer().getUniqueId()), storageData, StorageGUI.pluginName);
            InventoryGUI inventoryGUI = new InventoryGUI(e.getPlayer());
            inventoryGUI.openPage(new MainStoragePage(inventoryGUI, storageData));
        }

    }
    **/

    /*
    @EventHandler
    public void onInteractChest(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null || e.getClickedBlock().getType() != Material.CHEST)
            return;

        e.setCancelled(true); // ここは同期なので即処理

        Player player = e.getPlayer();
        UUID playerUUID = player.getUniqueId();

        // 非同期でDBアクセス
        Bukkit.getScheduler().runTaskAsynchronously(StorageGUI.getInstance(), () -> {
            GroupData group = DataIO.loadGroupData(playerUUID.toString());

            // 結果をもとに同期でGUIを開く
            Bukkit.getScheduler().runTask(StorageGUI.getInstance(), () -> {
                if (group != null && GUIUtils.openStorageGUI(player, group)) {
                    // 正常に開けた
                } else {
                    InventoryGUI gui = new InventoryGUI(player);
                    gui.openPage(new CreatePrivateGroupPage(gui));
                }
            });
        });
    }

     */
}
