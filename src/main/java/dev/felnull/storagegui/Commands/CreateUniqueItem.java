package dev.felnull.storagegui.Commands;

import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.GUI.Page.UniqueItemPage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CreateUniqueItem implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        InventoryGUI gui = new InventoryGUI((Player) sender);
        gui.openPage(new UniqueItemPage(gui));
        return false;
    }
}
