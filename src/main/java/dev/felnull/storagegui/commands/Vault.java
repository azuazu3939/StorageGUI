package dev.felnull.storagegui.commands;

import dev.felnull.Data.GroupData;
import dev.felnull.DataIO.DataIO;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.StorageGUI;
import dev.felnull.storagegui.gui.Page.CreatePrivateGroupPage;
import dev.felnull.storagegui.utils.GUIUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class Vault implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (!(commandSender instanceof Player player)) return false;
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
        return false;
    }
}
