package ru.otus.basic.yampolskiy.animals;

public class Dog extends Animal{

    public Dog(String nickname, int runningSpeed, int swimmingSpeed, int initialEndurance) {
        super("Dog", nickname, runningSpeed, swimmingSpeed, initialEndurance);
    }

    @Override
    public int swim(int distance) {
        int timeElapsed =0;
        if (distance > currentEndurance / DOG_SWIM_COEFFICIENT) {
            System.out.printf("Пес " + nickname +" проплыл %dм и Устал!", currentEndurance / DOG_SWIM_COEFFICIENT);
            timeElapsed = currentEndurance / DOG_SWIM_COEFFICIENT / swimmingSpeed;
            currentEndurance = 0;
            isTired = true;
            return timeElapsed;
        }
        timeElapsed = distance / swimmingSpeed;
        currentEndurance -= distance * DOG_SWIM_COEFFICIENT;
        return timeElapsed;
    }

    @Override
    public void info() {
        System.out.println(this);
    }
}
