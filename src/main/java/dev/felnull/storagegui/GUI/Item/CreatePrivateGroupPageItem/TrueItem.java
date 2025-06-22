package dev.felnull.storagegui.GUI.Item.CreatePrivateGroupPageItem;

import dev.felnull.BetterStorage;
import dev.felnull.Data.GroupData;
import dev.felnull.Data.StorageData;
import dev.felnull.DataIO.DataIO;
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
import java.util.HashSet;

public class TrueItem extends GUIItem {
    public TrueItem(InventoryGUI gui) {
        super(gui, new ItemStack(Material.GREEN_STAINED_GLASS));
        setDisplayName(GUIUtils.f("&6ストレージを作成!"));
        setLore(Arrays.asList(GUIUtils.c("&6Storageを作成する場合はこちらを押してください"),
                GUIUtils.c("&4注意!:"),
                GUIUtils.c("&4もしストレージを作成済みでこの画面が出た場合は"),
                GUIUtils.c("&4作成せず運営に相談してください")));
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();

        // ストレージデータの仮作成（空の状態）
        StorageData storageData = new StorageData(
                new HashSet<>(),              // requireBankPermission → 空
                null,                         // storageInventory → nullでOK（中で空Mapにされる）
                0.0                           // bankMoney → 初期は0
        );

        // グループデータを作成（個人グループ）
        GroupData groupData = new GroupData(player, storageData, StorageGUI.pluginName);

        // DBへ保存（version=0 なので新規作成用とする）
        DataIO.saveGroupData(BetterStorage.BSPlugin.getDatabaseManager(), groupData, 0L);

        // GUIを開く
        GUIUtils.openStorageGUI(player, player.getUniqueId().toString());
    }

}
