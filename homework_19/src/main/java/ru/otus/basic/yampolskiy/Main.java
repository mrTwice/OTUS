package ru.otus.basic.yampolskiy;

import ru.otus.basic.yampolskiy.base.*;

public class Main {
    public static void main(String[] args) {
        FruitFactory fruitFactory = new FruitFactory();

        Box<Apple> appleBox = new Box<>();
        appleBox.add(fruitFactory.getApple());
        printInfo("appleBox",appleBox.getWeight());

        Box<Orange> orangeBox = new Box<>();
        orangeBox.add(fruitFactory.getOrange());
        printInfo("orangeBox", orangeBox.getWeight());

        Box<Fruit> fruitBox = new Box<>();
        fruitBox.add(fruitFactory.getFruit());
        fruitBox.add(fruitFactory.getApple());
        fruitBox.add(fruitFactory.getOrange());
        printInfo("fruitBox", fruitBox.getWeight());

        Box<Fruit> fruitBox2 = new Box<>();
        fruitBox2.add(fruitFactory.getFruit());
        fruitBox2.add(fruitFactory.getApple());
        fruitBox2.add(fruitFactory.getOrange());
        printInfo("fruitBox2", fruitBox2.getWeight());

        compareBox("fruitBox","fruitBox2", fruitBox, fruitBox2);

        moveFruits("appleBox", appleBox, "fruitBox", fruitBox);
        moveFruits("fruitBox", fruitBox, "fruitBox2", fruitBox2);

    }

    private static void printInfo(String boxName, double weight) {
        System.out.printf("Вес коробки %s: %s\n", boxName, weight);
    }

    private static void compareBox(String box1Name, String box2Name, Box<? extends Fruit> box1, Box<? extends Fruit> box2) {
        System.out.printf("Результат сравнения коробок %s и %s: %s\n", box1Name, box2Name,box1.compare(box2));
    }

    private static <T extends Fruit> void moveFruits(String fromBoxName, Box<T> fromBox, String toBoxName, Box<? super T> toBox) {
        fromBox.moveFruitsTo(toBox);
        System.out.printf("Пересыпали фрукты из коробки %s в коробку %s\n", fromBoxName, toBoxName);
        printInfo(fromBoxName, fromBox.getWeight());
        printInfo(toBoxName, toBox.getWeight());
    }
}