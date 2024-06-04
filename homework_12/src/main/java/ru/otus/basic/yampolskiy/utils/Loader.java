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

    public static Map<String, Contact> loadFromFile() {
        Map<String, Contact> contacts = new TreeMap<>(Comparator.comparing(String::toLowerCase));
        String directoryPath = DIRECTORY_PATH;

        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            System.err.println("Директория " + directoryPath + " не существует или не является директорией.");
            return contacts;
        }

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
                        StringBuilder fileContent = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            fileContent.append(line).append(System.lineSeparator());
                        }

                        Contact contact = ContactParser.parseContact(fileContent.toString());
                        if (contact != null) {
                            String key = contact.getLastName() + "_" + contact.getFirstName();
                            contacts.put(key, contact);
                        }
                    } catch (IOException e) {
                        System.err.println("Произошла ошибка при чтении файла: " + file.getName() + " - " + e.getMessage());
                    }
                }
            }
        }

        return contacts;
    }
}
