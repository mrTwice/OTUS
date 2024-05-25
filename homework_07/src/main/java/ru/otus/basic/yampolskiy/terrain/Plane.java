package ru.otus.basic.yampolskiy.terrain;

import ru.otus.basic.yampolskiy.interfaces.LivingBeing;
import ru.otus.basic.yampolskiy.driver.Human;
import ru.otus.basic.yampolskiy.interfaces.Terrain;
import ru.otus.basic.yampolskiy.vehicle.Horse;

public class Plane implements Terrain {

    private final int HUMAN_OVERCOMING_COEFFICIENT = 1;
    private final int HORSE_OVERCOMING_COEFFICIENT = 5;


    @Override
    public String getType() {
        return "Равнина";
    }

    @Override
    public double getOvercomingCoefficient(LivingBeing livingBeing) {
        if(livingBeing instanceof Human) {
            return HUMAN_OVERCOMING_COEFFICIENT;
        } else if (livingBeing instanceof Horse) {
            return HORSE_OVERCOMING_COEFFICIENT;
        } else {
            return 1;
        }
    }
}
