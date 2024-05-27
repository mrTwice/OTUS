package ru.otus.basic.yampolskiy.vehicle;

import ru.otus.basic.yampolskiy.interfaces.Driver;
import ru.otus.basic.yampolskiy.driver.Human;
import ru.otus.basic.yampolskiy.interfaces.Terrain;
import ru.otus.basic.yampolskiy.interfaces.Transport;

import java.util.HashSet;
import java.util.Set;

public class Car implements Transport {

    private final Set<String> carUnCompatibleTerrains = new HashSet<>();
    private final int FUEL_CONSUMPTION = 10;
    private int fuel;
    private Driver driver;

    public Car(int fuel) {
        this.fuel = fuel;
        carUnCompatibleTerrains.add("Болото");
        carUnCompatibleTerrains.add("Густой Лес");
    }

    @Override
    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    @Override
    public void removeDriver() {
        driver = null;
    }


    @Override
    public String getType() {
        return "Машина";
    }

    @Override
    public int move(Terrain terrain, int distance) {
        if(driver == null) {
            System.out.println("Автомобиль не может ехать без водителя");
            return 0;
        }
        if(carUnCompatibleTerrains.contains(terrain.getType())){
            System.out.printf("Автомобиль не может ехать по %s.\n", terrain.getType());
            return 0;
        }
        Human human = (Human) driver;
        int overcomingDistance =  fuel * 100 / FUEL_CONSUMPTION;
        if (distance > overcomingDistance) {
            fuel = 0;
            System.out.printf("%s не смог полностью преодолеть %s км по %s на автомобиле.\n", human.getName(), distance, terrain.getType());
            System.out.printf("%s проехал только %s км по %s.\n", human.getName(), overcomingDistance, terrain.getType());
            System.out.printf("Сейчас в баке %d литров. Автомобилю нужна заправка.\n", fuel);
            return overcomingDistance;
        }

        System.out.printf("%s преодолел %s км по %s на автомобиле.\n", human.getName(), distance, terrain.getType());
        return distance;
    }
}
