package ru.otus.basic.yampolskiy.mvp;

import java.util.Scanner;

public class View {

    private final Scanner input = new Scanner(System.in);

    public void printMenu() {
        System.out.println("\n");
        System.out.print(
                """
                        1. Зарегистрироваться
                        2. Авторизоваться
                        3. Чат
                        4. Выйти

                        Введите номер пункта меню: \s"""
        );
    }

    public String getInput(String message) {
        System.out.print(message);
        return input.next();
    }

    public int getChoice(String message) {
        System.out.print(message);
        return input.nextInt() ;
    }

    public int getOperation() {
        return input.nextInt();
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

}
