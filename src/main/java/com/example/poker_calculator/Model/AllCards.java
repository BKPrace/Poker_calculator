package com.example.poker_calculator.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


// všechny karty. poslední dva bity čísla jsou barva, zbytek čísla je hodnota 1 -13
// jejich hodnota se zjišťuje za pomocí >>2(deleni ctyrmi) a barva s &3(zbytek po deleni ctyrmi),
// a jsou na to funkce v classe Combination, kde se vyuzivaji

public class AllCards {
    private static List<Integer> list = new ArrayList<>(Arrays.asList(8, 9, 10, 11, 12, 13, 14, 15, 16, 17,
            18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44,
            45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59));

    public static List<Integer> getList() {
        return List.copyOf(list);
    }
}
