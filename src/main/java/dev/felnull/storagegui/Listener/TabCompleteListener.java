package dev.felnull.storagegui.Listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import dev.felnull.storagegui.StorageGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TabCompleteListener extends PacketAdapter {
    private final StorageGUI plugin;

    public TabCompleteListener() {
        super(StorageGUI.INSTANCE, PacketType.Play.Client.TAB_COMPLETE);
        this.plugin = StorageGUI.INSTANCE;
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {
        Player player = event.getPlayer();

        // 補完が有効なプレイヤーのみ処理
        if (!plugin.isTabCompletionEnabled(player)) {
            return;
        }

        // プレイヤーが入力している内容を取得
        String input = event.getPacket().getStrings().read(0);

        // 候補を生成 (Material名)
        List<String> suggestions = Arrays.stream(Material.values())
                .map(Material::name)
                .filter(name -> name.startsWith(input.toUpperCase()))
                .collect(Collectors.toList());

        // TAB 補完結果を送信
        event.getPacket().getModifier().write(0, suggestions);
    }
}
