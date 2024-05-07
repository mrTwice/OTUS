package ru.otus.basic.yampolskiy.animals;

public class Horse extends Animal{

    public Horse(String nickname, int runningSpeed, int swimmingSpeed, int initialEndurance) {
        super("Horse", nickname, runningSpeed, swimmingSpeed, initialEndurance);
    }

    @Override
    public int swim(int distance) {
        int timeElapsed =0;
        if (distance > currentEndurance / HORSE_SWIM_COEFFICIENT) {
            System.out.printf("Конь " + nickname +" проплыл %dм и Устал!", currentEndurance / HORSE_SWIM_COEFFICIENT);
            timeElapsed = currentEndurance / HORSE_SWIM_COEFFICIENT / swimmingSpeed;
            currentEndurance = 0;
            isTired = true;
            return timeElapsed;
        }
        timeElapsed = distance / swimmingSpeed;
        currentEndurance -= distance * HORSE_SWIM_COEFFICIENT;
        return timeElapsed;
    }

    @Override
    public void info() {
        System.out.println(this);
    }
}
