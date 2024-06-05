package ru.otus.basic.yampolskiy.utils;

import ru.otus.basic.yampolskiy.PhoneBook;
import ru.otus.basic.yampolskiy.entities.Contact;
import ru.otus.basic.yampolskiy.entities.PhoneNumber;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Saver {

    private static final String DIRECTORY_PATH = "PhoneBook";

    public static void saveToFile(PhoneBook phoneBook) {
        for (Contact c : phoneBook.getContacts()) {
            StringBuilder contact = new StringBuilder();
            contact.append("firstName").append(":").append(c.getFirstName()).append("\n")
                    .append("lastName").append(":").append(c.getLastName()).append("\n")
                    .append("\n");
            for (PhoneNumber pn : c.getPhones()) {
                contact.append(pn.getTitle()).append(":").append(pn.getNumber()).append("\n");
            }
            createFile(c, contact.toString());
        }
    }

    private static void createFile(Contact contact, String strContact) {
        String fileName = contact.getLastName() + "_" + contact.getFirstName() + ".txt";
        File directory = new File(DIRECTORY_PATH);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                System.err.println("Не удалось создать директорию: " + DIRECTORY_PATH);
                return;
            }
        }
        writeContactToFile(strContact, new File(directory, fileName));
    }

    private static void writeContactToFile(String strContact, File file) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            writer.write(strContact);
            System.out.println("Контактная карточка создана: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Произошла ошибка при создании файла: " + file.getName() + " - " + e.getMessage());
        }
    }
}
