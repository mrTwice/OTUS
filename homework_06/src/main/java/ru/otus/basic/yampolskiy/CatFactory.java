package ru.otus.basic.yampolskiy;

import java.util.Random;

public class CatFactory {
    private static Random rnd = new Random();

    public static Cat createCat(String name) {
        return new Cat(name, rnd.nextInt(1, 10));
    }
}
