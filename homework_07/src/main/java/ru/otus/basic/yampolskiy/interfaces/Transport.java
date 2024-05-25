package ru.otus.basic.yampolskiy.interfaces;

public interface Transport {

    String getType();
    int move (Terrain terrain, int distance, Driver driver);
}
