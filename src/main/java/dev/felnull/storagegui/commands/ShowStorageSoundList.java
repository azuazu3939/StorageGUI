package dev.felnull.storagegui.commands;

import dev.felnull.storagegui.config.SoundList;
import dev.felnull.storagegui.data.SoundData;
import dev.felnull.storagegui.data.StorageSoundENUM;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;

public class ShowStorageSoundList implements CommandExecutor {
    //略称SSSL

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Map<StorageSoundENUM, Set<SoundData>> soundDataEnumMap = SoundList.soundEnumMap;
        sender.sendMessage("----SoundList----");
        for(StorageSoundENUM storageSoundENUM : StorageSoundENUM.values()) {
            Set<SoundData> soundDataSet = soundDataEnumMap.get(storageSoundENUM);
            sender.sendMessage(Component.text(storageSoundENUM.name).color(NamedTextColor.GOLD).decorate(TextDecoration.BOLD));
            for(SoundData soundData : soundDataSet) {
                sender.sendMessage(Component.text(soundData.soundName)
                        .hoverEvent(HoverEvent.showText(Component.text(soundData.soundNode)))
                );
            }
        }
        sender.sendMessage("-----------------");
        return true;
    }


}
