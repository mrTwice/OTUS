package ru.otus.basic.yampolskiy.terrain;

import ru.otus.basic.yampolskiy.interfaces.LivingBeing;
import ru.otus.basic.yampolskiy.interfaces.Terrain;

public class Swamp implements Terrain {
    private final double HUMAN_OVERCOMING_COEFFICIENT = 0.5;
    @Override
    public String getType() {
        return "Болото";
    }

    @Override
    public double getOvercomingCoefficient(LivingBeing livingBeing) {
        return HUMAN_OVERCOMING_COEFFICIENT;
    }
}
