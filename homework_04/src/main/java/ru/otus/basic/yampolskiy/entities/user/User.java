package ru.otus.basic.yampolskiy.entities.user;

public class User {
    private String firstName;
    private String lastName;
    private String patronymic;
    private int yearOfBirth;
    private String email;

    public User() {
    }

    public User(String firstName, String lastName, String patronymic, int yearOfBirth, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.yearOfBirth = yearOfBirth;
        this.email = email;
    }

    public void printInfo() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "ФИО: " + lastName + " " + firstName + " " + patronymic + "\n" +
                "Год рождения: " + yearOfBirth + "\n" +
                "e-mail: " + email + "\n";
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
