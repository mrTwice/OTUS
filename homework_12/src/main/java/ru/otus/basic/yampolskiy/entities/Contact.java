package ru.otus.basic.yampolskiy.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Contact {
    private String firstName;
    private String lastName;
    private final Map<String, PhoneNumber> phones;

    public Contact(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phones = new HashMap<>();
    }

    public Map<String, PhoneNumber> getPhones() {
        return phones;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullname() {
        return lastName + " " + firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public PhoneNumber addNewPhoneNumber(PhoneNumber phoneNumber) {
        return phones.putIfAbsent(phoneNumber.getTitle(), phoneNumber);
    }

    public boolean  removePhoneNumber (PhoneNumber phoneNumber) {
        return phones.remove(phoneNumber.getTitle(), phoneNumber);
    }

    @Override
    public String toString() {
        return lastName + " " + firstName + " " + phones;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(firstName, contact.firstName) && Objects.equals(lastName, contact.lastName) && Objects.equals(phones, contact.phones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, phones);
    }

}
