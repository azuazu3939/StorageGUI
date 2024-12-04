package dev.felnull.storagegui.Commands;

import dev.felnull.storagegui.Config.SoundList;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadSound implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        SoundList.loadSoundList();
        sender.sendMessage(Component.text("[StorageGUI]:").color(NamedTextColor.AQUA)
                .append(Component.text("サウンドを再読み込みしました").color(NamedTextColor.WHITE)));
        return false;
    }
}
