package ru.otus.basic.yampolskiy.animals;

public abstract class Animal {
    protected String nickname;
    protected int runningSpeed;
    protected int swimmingSpeed;
    protected final int initialEndurance;
    protected int currentEndurance;
    protected boolean isTired = false;
    protected String animalType;
    protected Integer swimCoefficient;

    public Animal(String animalType, String nickname, int runningSpeed, int swimmingSpeed, int initialEndurance, Integer swimCoefficient) {
        this.animalType = animalType;
        this.nickname = nickname;
        this.runningSpeed = runningSpeed;
        this.swimmingSpeed = swimmingSpeed;
        this.initialEndurance = initialEndurance;
        this.currentEndurance = initialEndurance;
        this.swimCoefficient = swimCoefficient;
    }

    public int run(int distance) {
        if(distance < 0) {
            throw new RuntimeException("Отрицательное значение дистанции");
        }
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

    public void setCurrentEndurance(int currentEndurance) {
        if(currentEndurance < 0) {
            throw new RuntimeException("Отрицательное значение выносливости");
        }
        this.currentEndurance = currentEndurance;
        isTired = false;
        System.out.printf("%s %s восстановил силы на %d ед.\n", animalType, nickname, currentEndurance);
    }

    public String getStatus() {
        if(isTired)
            return "устал";
        else
            return "бодр";
    }

    public int swim(int distance) {
        if(distance < 0) {
            throw new RuntimeException("Отрицательное значение дистанции");
        }
        if(swimCoefficient == null){
            System.out.println(animalType + " " + nickname + " пошел ко дну.");
            return -1;
        }
        int timeElapsed =0;
        if (distance > currentEndurance / swimCoefficient) {
            System.out.printf(animalType + " " + nickname +" проплыл %dм и Устал!", currentEndurance / swimCoefficient);
            timeElapsed = currentEndurance / swimCoefficient / swimmingSpeed;
            currentEndurance = 0;
            isTired = true;
            return timeElapsed;
        }
        timeElapsed = distance / swimmingSpeed;
        currentEndurance -= distance * swimCoefficient;
        return timeElapsed;
    }

    public abstract void info();

    @Override
    public String toString() {
        return animalType +
                " " + nickname  +
                " " + getStatus() +
                ". Выносливость: " + currentEndurance + " ед.";
    }
}
