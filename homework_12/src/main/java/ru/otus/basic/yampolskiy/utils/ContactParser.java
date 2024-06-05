package ru.otus.basic.yampolskiy.utils;

import ru.otus.basic.yampolskiy.entities.Contact;
import ru.otus.basic.yampolskiy.entities.PhoneNumber;

public class ContactParser {
    public static Contact parseContact(String fileContent) {
        try {
            // Разделение файла на поля и номера телефонов
            String[] sections = fileContent.split("\n\n");
            if (sections.length != 2) {
                throw new IllegalArgumentException("Неверный формат файла контакта");
            }

            // Обработка полей
            String[] fields = sections[0].split("\n");
            String firstName = parseField(fields[0], "FirstName");
            String lastName = parseField(fields[1], "LastName");

            // Создание нового контакта
            Contact newContact = new Contact(firstName, lastName);

            // Обработка телефонных номеров
            String[] phones = sections[1].split("\n");
            for (String phone : phones) {
                String[] phoneParts = phone.split(":");
                if (phoneParts.length != 2) {
                    throw new IllegalArgumentException("Неверный формат телефонного номера");
                }
                newContact.addNewPhoneNumber(new PhoneNumber(phoneParts[0], phoneParts[1]));
            }

            return newContact;
        } catch (Exception e) {
            System.err.println("Ошибка при парсинге контакта: " + e.getMessage());
            return null;
        }
    }

    private static String parseField(String fieldLine, String fieldName) {
        int index = fieldLine.indexOf(":");
        if (index == -1) {
            throw new IllegalArgumentException("Неверный формат поля: " + fieldName);
        }
        return fieldLine.substring(index + 1).trim();
    }
}
