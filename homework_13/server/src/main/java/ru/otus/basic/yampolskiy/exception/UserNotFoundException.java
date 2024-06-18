package ru.otus.basic.yampolskiy.exception;

public class UserNotFoundException extends Exception{
    private String description;
    private String value;

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String description, String value) {
        this.description = description;
        this.value = value;
    }

    public String getMessage() {
        return description + ": " + value;
    }
}
