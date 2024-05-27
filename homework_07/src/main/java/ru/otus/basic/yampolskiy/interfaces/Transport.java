package ru.otus.basic.yampolskiy.interfaces;

public interface Transport {

    void setDriver(Driver driver);
    void removeDriver();
    String getType();
    int move (Terrain terrain, int distance);
}
