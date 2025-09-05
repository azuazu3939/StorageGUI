package dev.felnull.storagegui.commands;

import dev.felnull.storagegui.StorageGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class GUIReload implements CommandExecutor {

    private final StorageGUI plugin;

    private static String PAGE_BACK, RETURN_MAIN, BUY_STORAGE, BUY_FAIL, ADD_VIEW_POS, SUB_VIEW_POS, SCROLL_POS;
    private static String SYMBOL;

    public GUIReload(StorageGUI storageGUI) {
        this.plugin = storageGUI;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        plugin.reloadConfig();
        SYMBOL = plugin.getConfig().getString("symbol");

        PAGE_BACK = plugin.getConfig().getString("messages.page-back", "§r§6§lストレージオプションに戻る");
        RETURN_MAIN = plugin.getConfig().getString("messages.return-main", "§r§c§lキャンセルする");
        BUY_STORAGE = plugin.getConfig().getString("messages.buy-storage", "§r§e§l%value%" + SYMBOL + "でストレージを購入する!");
        BUY_FAIL = plugin.getConfig().getString("messages.buy-fail", "§r§c§lお金が %lack%" + SYMBOL + " 足りません! 現在: %value%" + SYMBOL);
        ADD_VIEW_POS = plugin.getConfig().getString("messages.add-view-pos", "§r§a§lスロットPosを上げる");
        SUB_VIEW_POS = plugin.getConfig().getString("messages.sub-view-pos", "§r§b§lスロットPosを下げる");
        SCROLL_POS = plugin.getConfig().getString("messages.scroll-pos", "§r§6§l下にスクロール");
        return true;
    }

    public static String getPageBack() {
        return PAGE_BACK;
    }

    public static String getReturnMain() {
        return RETURN_MAIN;
    }

    public static String getBuyStorage() {
        return BUY_STORAGE;
    }

    public static String getBuyFail() {
        return BUY_FAIL;
    }

    public static String getAddViewPos() {
        return ADD_VIEW_POS;
    }

    public static String getSubViewPos() {
        return SUB_VIEW_POS;
    }

    public static String getScrollPos() {
        return SCROLL_POS;
    }

    @SuppressWarnings("unused")
    public static String getSymbol() {
        return SYMBOL;
    }
}
