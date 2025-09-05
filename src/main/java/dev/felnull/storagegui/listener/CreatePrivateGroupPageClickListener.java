package dev.felnull.storagegui.listener;

import dev.felnull.bettergui.core.GUIItem;
import dev.felnull.bettergui.core.GUIPage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class CreatePrivateGroupPageClickListener implements Listener {
    private final GUIPage page;

    public CreatePrivateGroupPageClickListener(GUIPage page) {
        this.page = page;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getView().getTopInventory() == this.page.inventory) {
            GUIItem item = this.page.getItem(e.getSlot());
            if (item != null) {
                switch (e.getClick()) {
                    case RIGHT:
                        item.onRightClick(e);
                        item.onClick(e);
                        break;
                    case LEFT:
                        item.onLeftClick(e);
                        item.onClick(e);
                        break;
                    case MIDDLE:
                        item.onMiddleClick(e);
                        break;
                    case DOUBLE_CLICK:
                        item.onDoubleClick(e);
                        break;
                    case SHIFT_RIGHT:
                        item.onShiftClick(e);
                        item.onShiftRightClick(e);
                        break;
                    case SHIFT_LEFT:
                        item.onShiftClick(e);
                        item.onShiftLeftClick(e);
                        break;
                    case DROP:
                        item.onDropClick(e);
                        break;
                    case CONTROL_DROP:
                        item.onControlDropClick(e);
                        break;
                    case SWAP_OFFHAND:
                        item.onOffhandClick(e);
                        break;
                    case NUMBER_KEY:
                        item.onNumberClick(e);
                        break;
                    case CREATIVE:
                        item.onCreativeClick(e);
                }
            }

            switch (e.getClick()) {
                case RIGHT:
                    if (e.getSlot() == -999) {
                        this.page.onOutsideWindowClick(e);
                        this.page.onOutsideWindowRightClick(e);
                    }
                    break;
                case LEFT:
                    if (e.getSlot() == -999) {
                        this.page.onOutsideWindowClick(e);
                        this.page.onOutsideWindowLeftClick(e);
                    }
                    break;
                case WINDOW_BORDER_RIGHT:
                    this.page.onWindowBorderRightClick(e);
                    break;
                case WINDOW_BORDER_LEFT:
                    this.page.onWindowBorderLeftClick(e);
            }

            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (e.getView().getTopInventory() == this.page.inventory) {
            this.page.close();
        }
    }
}
