package dev.felnull.storagegui.data;

public enum StorageSoundENUM {
    STORAGE_OPEN("StorageOpen"),
    STORAGE_CLOSE("StorageClose"),
    PAGE_SCROLL("PageScroll"),
    CHANGE_PAGE("ChangePage"),
    BUY("Buy");

    public final String name;

    StorageSoundENUM(String name) {
        this.name = name;
    }
}
