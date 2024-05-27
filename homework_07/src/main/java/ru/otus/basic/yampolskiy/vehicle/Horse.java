package ru.otus.basic.yampolskiy.vehicle;

import ru.otus.basic.yampolskiy.interfaces.LivingBeing;
import ru.otus.basic.yampolskiy.interfaces.Driver;
import ru.otus.basic.yampolskiy.driver.Human;
import ru.otus.basic.yampolskiy.interfaces.Terrain;
import ru.otus.basic.yampolskiy.interfaces.Transport;

import java.util.HashSet;
import java.util.Set;


public class Horse implements LivingBeing, Transport {
    private final Set<String> horseUnCompatibleTerrains = new HashSet<>();
    private final String nickname;
    private int stamina;
    private Driver driver;

    public Horse(String nickname, int stamina) {
        this.nickname = nickname;
        this.stamina = stamina;
        horseUnCompatibleTerrains.add("Болото");
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
        return "Лошадь";
    }

    @Override
    public int move(Terrain terrain, int distance) {
        if(driver == null) {
            System.out.println("Лошадь не может скакать без наездника");
            return 0;
        }
        if(horseUnCompatibleTerrains.contains(terrain.getType())){
            System.out.printf("Конь %s не может идти по %s.\n", nickname, terrain.getType());
            return 0;
        }
        Human human = (Human) driver;
        int overcomingDistance = (int) (stamina * terrain.getOvercomingCoefficient(this));
        if (distance > overcomingDistance) {
            stamina = 0;
            System.out.printf("%s не смог полностью преодолеть %s км по %s верхом на лошади по кличке %s.\n", human.getName(), distance, terrain.getType(), nickname);
            System.out.printf("%s преодолел только %s км по %s верхом на лошади по кличке %s.\n", human.getName(), overcomingDistance, terrain.getType(), nickname);
            System.out.printf("Коню %s нужен отдых.\n", nickname);
            return overcomingDistance;
        }

        System.out.printf("%s преодолел %s км по %s верхом на лошади по кличке %s.\n", human.getName(), distance, terrain.getType(), nickname);
        return distance;
    }
}
