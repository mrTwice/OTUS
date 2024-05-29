package ru.otus.basic.yampolskiy;

public class Cat {
    private final String nickname;
    private final int appetite;
    private boolean hungry;

    public Cat(String nickname, int appetite) {
        this.nickname = nickname;
        this.hungry = true;
        this.appetite = appetite;
    }

    public void toEat(Plate plate) {
        if (hungry && plate.getFood(appetite)) {
            hungry = false;
            System.out.println(nickname + " наелся");
        } else {
            System.out.println(nickname + " не доволен количеством еды.");
        }
    }

    public int getAppetite() {
        return appetite;
    }

    public String getNickname() {
        return nickname;
    }

    public boolean isHungry() {
        return hungry;
    }
}
