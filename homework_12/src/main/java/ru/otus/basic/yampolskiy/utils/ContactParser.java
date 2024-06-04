package ru.otus.basic.yampolskiy.utils;

import ru.otus.basic.yampolskiy.entities.Contact;
import ru.otus.basic.yampolskiy.entities.PhoneNumber;

public class ContactParser {
    public static Contact parseContact(String fileContent) {
        String[] lines = fileContent.split(System.lineSeparator());
        String firstName = null;
        String lastName = null;
        Contact contact = null;

        for (String line : lines) {
            if (line.startsWith("firstName:")) {
                firstName = line.substring("firstName:".length()).trim();
            } else if (line.startsWith("lastName:")) {
                lastName = line.substring("lastName:".length()).trim();
            } else if (line.contains(":")) {
                if (contact == null && firstName != null && lastName != null) {
                    contact = new Contact(firstName, lastName);
                }
                if (contact != null) {
                    String[] phoneParts = line.split(":", 2);
                    if (phoneParts.length == 2) {
                        String title = phoneParts[0].trim();
                        String number = phoneParts[1].trim();
                        contact.addNewPhoneNumber(new PhoneNumber(title, number));
                    }
                }
            }
        }
        return contact;
    }
}
