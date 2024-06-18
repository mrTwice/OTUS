package ru.otus.basic.yampolskiy;


public class Main {
    public static void main(String[] args) {
        Presenter presenter = new Presenter(new PhoneBook(), new View());
        presenter.create();
    }
}