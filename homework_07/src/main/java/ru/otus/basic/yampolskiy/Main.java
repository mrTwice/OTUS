package ru.otus.basic.yampolskiy;

import ru.otus.basic.yampolskiy.driver.Human;
import ru.otus.basic.yampolskiy.terrain.DenseForest;
import ru.otus.basic.yampolskiy.terrain.Plane;
import ru.otus.basic.yampolskiy.terrain.Swamp;
import ru.otus.basic.yampolskiy.interfaces.Terrain;
import ru.otus.basic.yampolskiy.vehicle.AllTerrainVehicle;
import ru.otus.basic.yampolskiy.vehicle.Bicycle;
import ru.otus.basic.yampolskiy.vehicle.Car;
import ru.otus.basic.yampolskiy.vehicle.Horse;
import ru.otus.basic.yampolskiy.interfaces.Transport;

public class Main {
    public static void main(String[] args) {
        Terrain terrain = new Plane();
        Terrain terrain1 = new Swamp();
        Terrain terrain2 = new DenseForest();
        Human human = new Human("Петр", 20);
        Transport transport = new Horse("Шустрый", 80);
        Transport transport1 = new Car(30);
        Transport transport2 = new Bicycle();
        Transport transport3 = new AllTerrainVehicle(340);

        human.getIntoVehicle(transport3);
        human.go(terrain, 1500);

    }


}