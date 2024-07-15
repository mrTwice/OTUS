package ru.otus.basic.yampolskiy.base;

public class Orange extends Fruit{
    private double weight = WEIGHT * 1.5;

    @Override
    public double getWeight() {
        return weight;
    }
}
