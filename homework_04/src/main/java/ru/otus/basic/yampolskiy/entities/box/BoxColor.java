package ru.otus.basic.yampolskiy.entities.box;

public enum BoxColor {
    BLACK("Черный"),
    WHITE("Белый"),
    RED("Красный"),
    GREEN("Зеленый"),
    BLUE("Синий"),
    YELLOW("Желтый"),
    ORANGE("Оранжевый"),
    CYAN("Голубой"),
    MAGENTA("Пурпурный"),
    VIOLET("Фиолетовый");

    private final String description;

    BoxColor(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
