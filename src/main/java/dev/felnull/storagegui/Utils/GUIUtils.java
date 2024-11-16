package dev.felnull.storagegui.Utils;

import dev.felnull.Data.InventoryData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GUIUtils {

    /*
    getNumericListは与えられたMapのキーの中で数字のみを抽出して、
    List<Integer>に入れて返すメソッド
     */
    public static List<Integer> getNumericKeyList(Map<String, InventoryData> map) {
        List<Integer> numericKeys = new ArrayList<>();
        for (String key : map.keySet()) {
            if (key.matches("\\d+")) { // キーが数字のみかチェック
                numericKeys.add(Integer.parseInt(key)); // Integerに変換してリストに追加
            }
        }
        return numericKeys;
    }

}
