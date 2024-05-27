package ru.otus.basic.yampolskiy.terrain;

import ru.otus.basic.yampolskiy.interfaces.LivingBeing;
import ru.otus.basic.yampolskiy.driver.Human;
import ru.otus.basic.yampolskiy.interfaces.Terrain;
import ru.otus.basic.yampolskiy.vehicle.Horse;

public class DenseForest implements Terrain {
    private final double HUMAN_OVERCOMING_COEFFICIENT = 0.8;
    private final int HORSE_OVERCOMING_COEFFICIENT = 4;
    @Override
    public String getType() {
        return "Густой Лес";
    }

    @Override
    public double getOvercomingCoefficient(LivingBeing livingBeing) {
        if(livingBeing instanceof Human) {
            return HUMAN_OVERCOMING_COEFFICIENT;
        } else if (livingBeing instanceof Horse) {
            return HORSE_OVERCOMING_COEFFICIENT;
        } else {
            return 0.1;
        }
    }
}
