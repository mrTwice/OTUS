package ru.otus.basic.yampolskiy.vehicle;

import ru.otus.basic.yampolskiy.driver.Human;
import ru.otus.basic.yampolskiy.interfaces.Driver;
import ru.otus.basic.yampolskiy.interfaces.Terrain;
import ru.otus.basic.yampolskiy.interfaces.Transport;

import java.util.HashSet;
import java.util.Set;

public class Bicycle implements Transport {

    private final int HUMAN_OVERCOMING_COEFFICIENT = 3;

    private final Set<String> bicycleUnCompatibleTerrains = new HashSet<>();

    public Bicycle() {
        bicycleUnCompatibleTerrains.add("Болото");
    }

    @Override
    public String getType() {
        return "Велосипед";
    }

    @Override
    public int move(Terrain terrain, int distance, Driver driver) {
        if(bicycleUnCompatibleTerrains.contains(terrain.getType())){
            System.out.printf("Велосипед не может ехать по %s.\n", terrain.getType());
            return 0;
        }
        Human human = (Human) driver;
        int overcomingDistance =  human.getStamina() * HUMAN_OVERCOMING_COEFFICIENT;
        human.setStamina(0);
        if (distance > overcomingDistance) {
            System.out.printf("%s не смог полностью преодолеть %s км по %s на велосипеде.\n", human.getName(), distance, terrain.getType());
            System.out.printf("%s проехал только %s км по %s.\n", human.getName(), overcomingDistance, terrain.getType());
            return overcomingDistance;
        }

        System.out.printf("%s преодолел %s км по %s на велосипеде.\n", human.getName(), distance, terrain.getType());
        return distance;
    }

}
