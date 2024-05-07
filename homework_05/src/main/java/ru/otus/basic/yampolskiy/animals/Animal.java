package ru.otus.basic.yampolskiy.animals;

public abstract class Animal {
    protected static final int DOG_SWIM_COEFFICIENT = 2;
    protected static final int HORSE_SWIM_COEFFICIENT = 4;
    protected String classType;
    protected String nickname;
    protected int runningSpeed;
    protected int swimmingSpeed;
    protected final int initialEndurance;
    protected int currentEndurance;
    protected boolean isTired = false;

    public Animal(String classType, String nickname, int runningSpeed, int swimmingSpeed, int initialEndurance) {
        this.classType = classType;
        this.nickname = nickname;
        this.runningSpeed = runningSpeed;
        this.swimmingSpeed = swimmingSpeed;
        this.initialEndurance = initialEndurance;
        this.currentEndurance = initialEndurance;
    }

    public int run(int distance) {
        String animalType = getAnimalType();
        int timeElapsed =0;
        if (distance > currentEndurance) {
            System.out.printf(animalType + " " + nickname + " пробежал %dм и Устал!", currentEndurance);
            timeElapsed = currentEndurance / runningSpeed;
            currentEndurance = 0;
            isTired = true;
            return timeElapsed;
        }
        timeElapsed = distance / runningSpeed;
        currentEndurance -= distance;
        return timeElapsed;
    }

    private String getAnimalType() {
        String animalType ="";
        switch (classType) {
            case "Cat" -> animalType = "Кот";
            case "Dog" -> animalType = "Пёс";
            case "Horse" -> animalType = "Конь";
            default -> animalType = "Неведома зверушка";
        }
        return animalType;
    }

    public void setCurrentEndurance(int currentEndurance) {
        this.currentEndurance = currentEndurance;
        String animalType = getAnimalType();
        isTired = false;
        System.out.printf("%s %s восстановил силы на %d ед.\n", animalType, nickname, currentEndurance);
    }

    public String getStatus() {
        if(isTired)
            return "устал";
        else
            return "бодр";
    }

    public abstract int swim(int distance);

    public abstract void info();

    @Override
    public String toString() {
        return getAnimalType() +
                " " + nickname  +
                " " + getStatus() +
                ". Выносливость: " + currentEndurance + " ед.";
    }
}
