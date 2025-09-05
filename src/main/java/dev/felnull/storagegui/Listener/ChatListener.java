package dev.felnull.storagegui.Listener;

import dev.felnull.storagegui.StorageGUI;
import io.papermc.paper.event.player.AsyncChatEvent;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ChatListener implements Listener {
    private final StorageGUI plugin;
    @EventHandler(priority = EventPriority.LOW)
    public void onChat(AsyncChatEvent e) {
        Player p = e.getPlayer();
        Component msg = e.message();
        StorageGUI.INSTANCE.tabCompletionEnabled.put(p, true);

        if (!plugin.getChatReader().isRegistered(p)) {
            return;
        }

        e.setCancelled(true);
        plugin.getServer().getScheduler().runTask(plugin, () -> {
            try {
                plugin.getChatReader().onChat(p, msg);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        plugin.getChatReader().unregisterNextChat(p);
        StorageGUI.INSTANCE.tabCompletionEnabled.remove(p);
    }
}
