package ru.otus.basic.yampolskiy.animals;

public class Horse extends Animal{


    public Horse(String nickname, int runningSpeed, int swimmingSpeed, int initialEndurance) {
        super("Конь", nickname, runningSpeed, swimmingSpeed, initialEndurance,4);
    }

    @Override
    public void info() {
        System.out.println(this);
    }
}
