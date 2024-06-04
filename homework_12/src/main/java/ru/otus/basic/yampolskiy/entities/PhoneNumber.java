package ru.otus.basic.yampolskiy.entities;

import java.util.Objects;

public class PhoneNumber {
    private String title;
    private final String number;

    public PhoneNumber(String title, String number) {
        this.title = title;
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return ": "  + number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneNumber that = (PhoneNumber) o;
        return Objects.equals(title, that.title) && Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, number);
    }
}
