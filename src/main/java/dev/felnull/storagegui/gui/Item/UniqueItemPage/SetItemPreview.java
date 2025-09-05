package dev.felnull.storagegui.gui.Item.UniqueItemPage;

import dev.felnull.bettergui.core.GUIItem;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.data.UniqueItemData;
import dev.felnull.storagegui.utils.GUIUtils;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class SetItemPreview extends GUIItem {

    public SetItemPreview(InventoryGUI gui, @NotNull UniqueItemData uniqueItemData) {
        super(gui, new ItemStack(uniqueItemData.material));
        ItemMeta meta = itemStack.getItemMeta();
        meta.lore(GUIUtils.splitLoreFactory(uniqueItemData.lore));
        meta.setCustomModelData(uniqueItemData.cmdNumber);
        meta.displayName(PlainTextComponentSerializer.plainText().deserialize(uniqueItemData.displayName));
        if(uniqueItemData.doEnchant){
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
        }else {
            meta.removeEnchant(Enchantment.UNBREAKING);
        }
        itemStack.setItemMeta(meta);
    }
}
