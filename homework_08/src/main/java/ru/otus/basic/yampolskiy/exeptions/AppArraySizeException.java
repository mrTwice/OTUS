package ru.otus.basic.yampolskiy.exeptions;

public class AppArraySizeException extends Exception{

    private int value;
    private String message;

    public AppArraySizeException( int value, String message) {
        this.value = value;
        this.message = message;
    }

    public String getMessage() {
        return message + value;
    }
}
