package ru.otus.basic.yampolskiy.interfaces;

public interface Terrain {
    String getType();
    double getOvercomingCoefficient(LivingBeing livingBeing);
}
