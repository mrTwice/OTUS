package ru.otus.basic.yampolskiy.exception;

public class UserAlreadyExistException extends Exception{
    private String description;
    private String value;

    public UserAlreadyExistException(String description, String value) {
        this.description = description;
        this.value = value;
    }

    public String getMessage() {
        return description + ": " + value;
    }
}
