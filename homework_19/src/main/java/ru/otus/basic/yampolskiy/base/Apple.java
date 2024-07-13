package ru.otus.basic.yampolskiy.base;

public class Apple extends Fruit{
    private final double weight =  WEIGHT * 1.1;

    @Override
    public double getWeight() {
        return weight;
    }
}
