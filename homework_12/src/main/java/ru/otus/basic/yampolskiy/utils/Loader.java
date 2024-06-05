package ru.otus.basic.yampolskiy.utils;

import ru.otus.basic.yampolskiy.entities.Contact;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class Loader {

    private static final String DIRECTORY_PATH = "PhoneBook";
    private static final Map<String, Contact> CONTACTS = new TreeMap<>(Comparator.comparing(String::toLowerCase));

    public static Map<String, Contact> loadFromFile() {
        File directory = new File(DIRECTORY_PATH);
        if (!directory.exists() || !directory.isDirectory()) {
            System.err.println("Директория " + DIRECTORY_PATH + " не существует или не является директорией.");
            return CONTACTS;
        }
        readFiles(directory.listFiles());
        return CONTACTS;
    }

    private static void readFiles(File[] files) {
        for (File file : files) {
            if (file.isFile()) {
                try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
                    StringBuilder fileContent = new StringBuilder();
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        fileContent.append(line).append(System.lineSeparator());
                    }

                    Contact contact = ContactParser.parseContact(fileContent.toString());
                    String key = contact.getLastName() + "_" + contact.getFirstName();
                    CONTACTS.put(key, contact);
                } catch (IOException e) {
                    System.err.println("Произошла ошибка при чтении файла: " + file.getName() + " - " + e.getMessage());
                }
            }
        }
    }
}
