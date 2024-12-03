package dev.felnull.storagegui.Commands;

import dev.felnull.storagegui.Config.SoundList;
import dev.felnull.storagegui.Data.SoundData;
import dev.felnull.storagegui.Data.StorageSoundENUM;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.Map;

public class ShowStorageSoundList implements CommandExecutor {
    //略称SSSL

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Map<StorageSoundENUM, SoundData> soundDataEnumMap = SoundList.loadSoundList();

        return true;
    }


}
