package ru.otus.basic.yampolskiy.exeptions;

public class AppArraySizeException extends Exception{

    private final int value;
    private final String message;

    public AppArraySizeException( int value, String message) {
        this.value = value;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message + value;
    }
}
