package ru.otus.basic.yampolskiy.driver;

import ru.otus.basic.yampolskiy.interfaces.LivingBeing;
import ru.otus.basic.yampolskiy.interfaces.Driver;
import ru.otus.basic.yampolskiy.interfaces.Terrain;
import ru.otus.basic.yampolskiy.interfaces.Transport;

public class Human implements Driver, LivingBeing {
    private final String name;
    private int stamina;
    private Transport transport;
    private boolean isDriver;

    public Human(String name, int stamina) {
        this.name = name;
        this.stamina = stamina;
    }

    public Human(String name, int stamina, Transport transport) {
        this.name = name;
        this.stamina = stamina;
        this.transport = transport;
    }

    public boolean go(Terrain terrain, int distance) {
        int currentDistance = distance;

        if(transport != null) {
            System.out.printf("%s использует %s.\n", name, transport.getType());
            int distanceOnTransport = transport.move(terrain, currentDistance);
            currentDistance -= distanceOnTransport;
            if(stamina == 0) {
                System.out.printf("%s преодолел %s по %s и ему нужен отдых.\n", name, distanceOnTransport, terrain.getType());
                return false;
            }
        }

        if (currentDistance == 0) {
            return true;
        }

        System.out.printf("%s решил идти пешком %s км по %s.\n", name, currentDistance, terrain.getType());
        int overcomingDistance = (int) (stamina * terrain.getOvercomingCoefficient(this));
        if (currentDistance > overcomingDistance) {
            System.out.printf("%s не смог полностью преодолеть %s км по %s.\n", name, currentDistance, terrain.getType());
            System.out.printf("%s остановился отдохнуть после %s км пути по %s из-а усталости.\n", name, overcomingDistance, terrain.getType());
            stamina = 0;
            return false;
        }

        System.out.printf("%s преодолел %s по %s\n", name, distance, terrain.getType());
        return true;
    }

    public String getName() {
        return name;
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    @Override
    public boolean getIntoVehicle(Transport transport) {
        if(isDriver) {
            System.out.println("Нельзя управлять двумя транспортными средствами одновременно");
            return false;
        }
        this.transport = transport;
        transport.setDriver(this);
        isDriver = true;
        return true;
    }

    @Override
    public boolean getOutVehicle() {
        if(transport != null){
            transport.removeDriver();
            transport = null;
            isDriver=false;
            return true;
        }
        System.out.printf("У %s нет транспорта", name);
        return false;
    }
}
