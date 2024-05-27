package ru.otus.basic.yampolskiy.vehicle;

import ru.otus.basic.yampolskiy.driver.Human;
import ru.otus.basic.yampolskiy.interfaces.Driver;
import ru.otus.basic.yampolskiy.interfaces.Terrain;
import ru.otus.basic.yampolskiy.interfaces.Transport;


public class AllTerrainVehicle implements Transport {
    private final int FUEL_CONSUMPTION = 25;
    private int fuel;
    private Driver driver;

    public AllTerrainVehicle(int fuel) {
        this.fuel = fuel;
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
        return "Вездеход";
    }

    @Override
    public int move(Terrain terrain, int distance) {
        if(driver == null) {
            System.out.println("Вездеход не может ехать без водителя");
            return 0;
        }
        Human human = (Human) driver;
        int overcomingDistance =  fuel * 100 / FUEL_CONSUMPTION;
        if (distance > overcomingDistance) {
            fuel = 0;
            System.out.printf("%s не смог полностью преодолеть %s км по %s на вездеходе.\n", human.getName(), distance, terrain.getType());
            System.out.printf("%s проехал только %s км по %s.\n", human.getName(), overcomingDistance, terrain.getType());
            System.out.printf("Сейчас в баке %d литров. Вездеходу нужна заправка.\n", fuel);
            return overcomingDistance;
        }

        System.out.printf("%s преодолел %s км по %s на вездеходе.\n", human.getName(), distance, terrain.getType());
        return distance;
    }
}
