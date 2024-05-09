package ru.otus.basic.yampolskiy.animals;

public class Cat extends Animal{

    public Cat(String nickname, int runningSpeed, int initialEndurance) {
        super("Кот", nickname, runningSpeed, 0, initialEndurance, null);
    }

    @Override
    public void info() {
        System.out.println(this);
    }
}
