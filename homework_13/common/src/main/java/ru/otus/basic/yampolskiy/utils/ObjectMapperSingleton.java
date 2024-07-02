package ru.otus.basic.yampolskiy.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ObjectMapperSingleton {
    private static final ObjectMapper INSTANCE = new ObjectMapper();

    private ObjectMapperSingleton( ){
        INSTANCE.registerModule(new JavaTimeModule());
    }

    public static ObjectMapper getINSTANCE() {
        return INSTANCE;
    }
}
