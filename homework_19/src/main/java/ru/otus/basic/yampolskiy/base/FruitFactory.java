package ru.otus.basic.yampolskiy.base;

public class FruitFactory {
    public Apple getApple(){
        return new Apple();
    }

    public Orange getOrange(){
        return new Orange();
    }

    public Fruit getFruit(){
        return new Fruit();
    }
}
