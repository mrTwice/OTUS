package ru.otus.basic.yampolskiy;

public class Main {

    private static final int MAX_AMOUNT_FOOD = 34;

    public static void main(String[] args) {

        Plate plate = new Plate(MAX_AMOUNT_FOOD);

        Cat[] cats = {
                CatFactory.createCat("Барсик"),
                CatFactory.createCat("Мурзик"),
                CatFactory.createCat("Плинтус"),
                CatFactory.createCat("Рыжик"),
                CatFactory.createCat("Соня"),
                CatFactory.createCat("Пончик")
        };

        for (Cat cat : cats) {
            System.out.println();
            System.out.printf("Кот %s подошел к тарелке\n", cat.getNickname());
            cat.toEat(plate);
            System.out.println();
        }


    }


}