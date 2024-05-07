package ru.otus.basic.yampolskiy.animals;

public class Cat extends Animal{

    public Cat(String nickname, int runningSpeed, int initialEndurance) {
        super("Cat", nickname, runningSpeed, 0, initialEndurance);
    }

    @Override
    public int swim(int distance) {
        System.out.println("Кот " + nickname + " пошел ко дну.");
        return -1;
    }

    @Override
    public void info() {
        System.out.println(this);
    }
}
