package dev.felnull.storagegui.GUI.Item.SoundOptionPage;

import dev.felnull.bettergui.core.GUIItem;
import dev.felnull.bettergui.core.InventoryGUI;
import dev.felnull.storagegui.GUI.Page.Option.SoundOptionPage;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class addPosition extends GUIItem {

    public SoundOptionPage soundOptionPage;

    public addPosition(InventoryGUI gui, SoundOptionPage soundOptionPage) {
        super(gui, new ItemStack(Material.BARRIER));
        setDisplayName(ChatColor.translateAlternateColorCodes('&',"&6下にスクロール"));
        this.soundOptionPage = soundOptionPage;
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        soundOptionPage.addSlotStartPosition(9);
    }
}
