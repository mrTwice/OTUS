package ru.otus.basic.yampolskiy.animals;

public class Dog extends Animal{

    public Dog(String nickname, int runningSpeed, int swimmingSpeed, int initialEndurance) {
        super("Пес", nickname, runningSpeed, swimmingSpeed, initialEndurance, 2);
    }

    @Override
    public void info() {
        System.out.println(this);
    }
}
