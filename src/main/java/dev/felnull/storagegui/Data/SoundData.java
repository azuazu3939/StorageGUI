package dev.felnull.storagegui.Data;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class SoundData {
    public final String soundName; //サウンドの表示名
    public final String soundNode; //サウンドのノード(テクスチャで指定)
    public final Set<StorageSoundENUM> storageSoundENUM; //そのサウンドが対応しているストレージの動作
    public ItemStack displayItem;  //サウンドの見た目

    public SoundData(@NotNull String soundName, @NotNull String soundNode, @NotNull Set<StorageSoundENUM> storageSoundENUM, int customModelData) {
        if (!soundNode.matches("[a-z0-9/._-]+")) {
            throw new IllegalArgumentException("無効なサウンドノード: " + soundNode);
        }
        this.soundName = soundName;
        this.soundNode = soundNode;
        this.storageSoundENUM = storageSoundENUM;
        this.displayItem = new ItemStack(Material.STICK);

        ItemMeta meta = this.displayItem.getItemMeta();
        if (meta != null) {
            meta.displayName(Component.text(soundName).color(NamedTextColor.GOLD));
            meta.setCustomModelData(customModelData);

            this.displayItem.setItemMeta(meta);
        }
    }
}
