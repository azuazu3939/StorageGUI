package dev.felnull.storagegui.GUI.Item.CreatePrivateGroupPageItem;

import dev.felnull.BetterStorage;
import dev.felnull.Data.GroupData;
import dev.felnull.Data.GroupPermENUM;
import dev.felnull.Data.InventoryData;
import dev.felnull.Data.StorageData;
import dev.felnull.DataIO.DataIO;
import dev.felnull.DataIO.GroupManager;
import dev.felnull.DataIO.UnifiedLogManager;
import dev.felnull.bettergui.core.GUIItem;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.StorageGUI;
import dev.felnull.storagegui.Utils.GUIUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class TrueItem extends GUIItem {
    public TrueItem(InventoryGUI gui) {
        super(gui, new ItemStack(Material.GREEN_STAINED_GLASS));
        setDisplayName(GUIUtils.f("&6ストレージを作成!"));
        setLore(Arrays.asList(
                GUIUtils.c("&6Storageを作成する場合はこちらを押してください"),
                GUIUtils.c("&4注意!:"),
                GUIUtils.c("&4もしストレージを作成済みでこの画面が出た場合は"),
                GUIUtils.c("&4作成せず運営に相談してください")
        ));
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();

        // 空のストレージデータを生成
        StorageData storageData = new StorageData(
                new HashSet<>(),              // requireBankPermission
                new HashMap<>(),              // storageInventory
                0.0                           // bankMoney
        );

        // 個人用グループを作成
        GroupData groupData = new GroupData(player, storageData, StorageGUI.pluginName);

        // 無料で 0〜26 のストレージを付与（各ページ 6行）
        Set<String> perm = new HashSet<>();
        perm.add(GroupPermENUM.MEMBER.getPermName());

        for (int i = 0; i <= 26; i++) {
            InventoryData invData = new InventoryData(null, 6, perm, new HashMap<>());
            storageData.storageInventory.put(String.valueOf(i), invData);
        }

        // 保存（新規作成用として version=0）
        DataIO.saveGroupData(groupData);
        GroupManager.registerGroup(groupData);
        UnifiedLogManager.saveBackupSnapshot(groupData);

        // GUIを開く
        GUIUtils.openStorageGUI(player, groupData);
    }
}
