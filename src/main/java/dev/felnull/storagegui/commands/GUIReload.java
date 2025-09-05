package dev.felnull.storagegui.commands;

import dev.felnull.storagegui.StorageGUI;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

public class GUIReload implements CommandExecutor {

    private final StorageGUI plugin;
    private static final FileConfiguration config = StorageGUI.getInstance().getConfig();

    private static String SYMBOL = config.getString("symbol", "$");
    private static String ADD_VIEW_POS = config.getString("messages.add-view-pos", "§r§aスロットPosを上げる");
    private static String SUB_VIEW_POS = config.getString("messages.sub-view-pos", "§r§bスロットPosを下げる");
    private static String RETURN_MAIN = config.getString("messages.return-main", "§r§cキャンセルする");
    private static String PAGE_BACK = config.getString("messages.page-back", "§r§6ストレージオプションに戻る");
    private static String BUY_STORAGE = config.getString("messages.buy-storage", "§r§e%value%" + SYMBOL + "でストレージを購入する!");
    private static String BUY_FAIL = config.getString("messages.buy-fail", "§r§cお金が %lack%" + SYMBOL + " 足りません! 現在: %value%" + SYMBOL);
    private static String SCROLL_POS = config.getString("messages.scroll-pos", "§r§6下にスクロール");

    public GUIReload(StorageGUI storageGUI) {
        this.plugin = storageGUI;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        plugin.reloadConfig();
        SYMBOL = plugin.getConfig().getString("symbol", "$");

        PAGE_BACK = plugin.getConfig().getString("messages.page-back", "§r§6ストレージオプションに戻る");
        RETURN_MAIN = plugin.getConfig().getString("messages.return-main", "§r§cキャンセルする");
        BUY_STORAGE = plugin.getConfig().getString("messages.buy-storage", "§r§e%value%" + SYMBOL + "でストレージを購入する!");
        BUY_FAIL = plugin.getConfig().getString("messages.buy-fail", "§r§cお金が %lack%" + SYMBOL + " 足りません! 現在: %value%" + SYMBOL);
        ADD_VIEW_POS = plugin.getConfig().getString("messages.add-view-pos", "§r§aスロットPosを上げる");
        SUB_VIEW_POS = plugin.getConfig().getString("messages.sub-view-pos", "§r§bスロットPosを下げる");
        SCROLL_POS = plugin.getConfig().getString("messages.scroll-pos", "§r§6下にスクロール");

        commandSender.sendMessage(Component.text("§aリロードが完了しました。"));
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
