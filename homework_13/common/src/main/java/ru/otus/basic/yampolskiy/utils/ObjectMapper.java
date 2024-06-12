package ru.otus.basic.yampolskiy.utils;

public class ObjectMapper {
    private static final ObjectMapper INSTANCE = new ObjectMapper();

    private ObjectMapper( ){}

    public static ObjectMapper getINSTANCE() {
        return INSTANCE;
    }
}
