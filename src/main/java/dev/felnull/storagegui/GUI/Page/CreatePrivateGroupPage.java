package dev.felnull.storagegui.GUI.Page;

import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.GUI.Item.CreatePrivateGroupPageItem.FalseItem;
import dev.felnull.storagegui.GUI.Item.CreatePrivateGroupPageItem.TrueItem;
import dev.felnull.storagegui.GUI.StorageGUIPage;
import dev.felnull.storagegui.Utils.GUIUtils;
import org.bukkit.Sound;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class CreatePrivateGroupPage extends StorageGUIPage {

    public CreatePrivateGroupPage(InventoryGUI gui) {
        super(gui, GUIUtils.f("&6ストレージを作成しますか？"), 3 * 9);
        GUIUtils.playStorageSound(Sound.ENTITY_VILLAGER_YES.key(), gui.player);
    }

    @Override
    public void setUp() {
        String[] layout = {
                "-ggg-rrr-",
                "-ggg-rrr-",
                "-ggg-rrr-"
        };

        for (int row = 0; row < layout.length; row++) {
            String line = layout[row];
            for (int col = 0; col < line.length(); col++) {
                char c = line.charAt(col);
                int index = row * 9 + col;

                switch (c) {
                    case 'g':
                        setItem(index, new TrueItem(gui));
                        break;
                    case 'r':
                        setItem(index, new FalseItem(gui));
                        break;
                    // '-' は空白なのでスキップ
                }
            }
        }
    }

    @Override
    public void back() {

    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }
}
