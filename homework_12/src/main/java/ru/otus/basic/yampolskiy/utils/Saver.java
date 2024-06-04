package ru.otus.basic.yampolskiy.utils;

import ru.otus.basic.yampolskiy.PhoneBook;
import ru.otus.basic.yampolskiy.entities.Contact;
import ru.otus.basic.yampolskiy.entities.PhoneNumber;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Saver {

    private static final String DIRECTORY_PATH = "PhoneBook";

    public static void saveToFile(PhoneBook phoneBook) {
        for (Contact c : phoneBook.getContacts()) {
            StringBuilder contact = new StringBuilder();
            contact.append("firstName").append(":").append(c.getFirstName()).append("\n")
                    .append("lastName").append(":").append(c.getLastName()).append("\n")
                    .append("\r\n");
            for (PhoneNumber pn : c.getPhones()) {
                contact.append(pn.getTitle()).append(":").append(pn.getNumber()).append("\n");
            }
            createContactCard(c, contact.toString());
        }
    }

    private static void createContactCard(Contact contact, String strContact) {
        String directoryPath = DIRECTORY_PATH;
        String fileName = contact.getLastName() + "_" + contact.getFirstName() + ".txt";

        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(directory, fileName);

        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file))) {
            byte[] data = strContact.getBytes(StandardCharsets.UTF_8);
            int offset = 0;
            int defaultBufferSize = 8192;

            while (offset < data.length) {
                int bytesToWrite = Math.min(defaultBufferSize, data.length - offset);
                bufferedOutputStream.write(data, offset, bytesToWrite);
                offset += bytesToWrite;
            }

            System.out.println("Контактная карточка создана: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Произошла ошибка при создании файла: " + e.getMessage());
        }
    }
}
