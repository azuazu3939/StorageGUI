package dev.felnull.storagegui.listener;

import dev.felnull.storagegui.StorageGUI;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.SQLException;

public class ChatListener implements Listener {
    private final StorageGUI plugin;

    public ChatListener(StorageGUI plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onChat(AsyncChatEvent e) {
        Player p = e.getPlayer();
        Component msg = e.message();
        StorageGUI.getInstance().tabCompletionEnabled.put(p, true);

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
        StorageGUI.getInstance().tabCompletionEnabled.remove(p);
    }
}
