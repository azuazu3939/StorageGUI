package dev.felnull.storagegui.Listener;

import dev.felnull.bettergui.core.GUIPage;
import dev.felnull.storagegui.GUI.Page.ModularStoragePage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

public class ModularStoragePageClickListener implements Listener {
    private final ModularStoragePage page;


    public ModularStoragePageClickListener(ModularStoragePage page){
        this.page = page;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getView().getTopInventory() != page.inventory) return;
        switch (e.getClick()){
            case WINDOW_BORDER_RIGHT:
                page.onWindowBorderRightClick(e);
                break;
            case WINDOW_BORDER_LEFT:
                page.onWindowBorderLeftClick(e);
                break;
            case RIGHT:
                if(e.getSlot() == -999){
                    page.onOutsideWindowClick(e);
                    page.onOutsideWindowRightClick(e);
                }
                break;
            case LEFT:
                if(e.getSlot() == -999){
                    page.onOutsideWindowClick(e);
                    page.onOutsideWindowLeftClick(e);
                }
                break;
        }
        page.cursorItem = e.getCursor() == null ? null : e.getCursor().clone();
    }
    //インベントリを閉じるときにインベントリ内のアイテムデータをInventoryDataに変換する必要がある
    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (e.getView().getTopInventory() != page.inventory) {
            return;
        }
        page.close();
    }
}
