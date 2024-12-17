package dev.felnull.storagegui.Commands;

import dev.felnull.storagegui.Config.SettingsConfig;
import dev.felnull.storagegui.Data.StorageSoundData;
import dev.felnull.storagegui.Data.StorageSoundENUM;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ShowNowSetSound  implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        StorageSoundData storageSoundData = SettingsConfig.loadSettings((Player) sender);

        for(StorageSoundENUM storageSoundENUM : StorageSoundENUM.values()){
            sender.sendMessage(storageSoundENUM.name + storageSoundData.soundENUMSoundMap.get(storageSoundENUM).soundName);
        }
        return false;
    }
}
