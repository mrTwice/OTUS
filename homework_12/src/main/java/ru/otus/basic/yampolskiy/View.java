package ru.otus.basic.yampolskiy;

import ru.otus.basic.yampolskiy.entities.Contact;

import java.util.List;
import java.util.Scanner;

public class View {
    private final Scanner consoleInput = new Scanner(System.in);

    public void printMenu() {
        System.out.println();
        System.out.println(
                """
                        1. Показать список контактов.
                        2. Добавить контакт.
                        3. Удалить контакт.
                        4. Найти контакт.
                        5. Найти контакт по номеру телефона.
                        6. Выход.
                        """
        );
    }

    public int getOperation() {
        return consoleInput.nextInt();
    }

    public int getChoice(String message) {
        System.out.print(message);
        return consoleInput.nextInt() ;
    }

    public void printContacts(List<Contact> contacts) {
        System.out.println();
        if(contacts.isEmpty())
            System.out.println("Список контактов пуст.");
        for (int i = 0; i < contacts.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + contacts.get(i));
        }
        System.out.println();
    }

    public String getInput(String message) {
        System.out.print(message);
        return consoleInput.next();
    }


    public void printInfo(String message) {
        System.out.println(message);
    }

}
