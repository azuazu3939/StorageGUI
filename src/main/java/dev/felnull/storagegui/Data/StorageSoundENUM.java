package dev.felnull.storagegui.Data;

public enum StorageSoundENUM {
    STORAGE_OPEN("StorageOpen"),
    STORAGE_CLOSE("StorageClose"),
    PAGE_SCROLL("PageScroll"),
    CHANGE_PAGE("ChangePage");

    public final String name;

    StorageSoundENUM(String name) {
        this.name = name;
    }
}
