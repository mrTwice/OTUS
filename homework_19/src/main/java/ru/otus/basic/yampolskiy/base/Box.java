package ru.otus.basic.yampolskiy.base;

import java.util.ArrayList;
import java.util.List;

public class Box <T extends Fruit>  implements Comparable<Box<? extends Fruit>>{
    private final List<T> fruits = new ArrayList<>();

    public void add(T fruit) {
        fruits.add(fruit);
    }

    public double getWeight() {
        return fruits.stream().mapToDouble(T::getWeight).sum();
    }

    public boolean compare(Box<? extends Fruit> box) {
        return this.compareTo(box) == 0;
    }

    public void moveFruitsTo(Box<? super T> box) {
        if (box == null || box == this) {
            return;
        }
        box.fruits.addAll(this.fruits);
        this.fruits.clear();
    }

    @Override
    public int compareTo(Box<? extends Fruit> o) {
        return Double.compare(this.getWeight(), o.getWeight());
    }
}
