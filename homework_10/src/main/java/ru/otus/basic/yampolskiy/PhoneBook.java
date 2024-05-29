package ru.otus.basic.yampolskiy;

import ru.otus.basic.yampolskiy.entities.Contact;

import java.util.*;
import java.util.stream.Collectors;

public class PhoneBook {
    private final Map<String, Contact> contacts = new TreeMap<>(Comparator.comparing(String::toLowerCase));

    public List<Contact> getContacts() {
        return new ArrayList<>(contacts.values());
    }

    public List<Contact> findContacts (String query) {
        return contacts
                .entrySet()
                .stream()
                .filter(entry->entry.getKey().contains(query))
                .map(Map.Entry::getValue)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public Contact addNew(Contact contact) {
        contacts.putIfAbsent(contact.getLastName() + " " + contact.getFirstName(), contact);
        return  contact;
    }

    public boolean remove(Contact contact) {
        return contacts.remove(contact.getLastName() + " " + contact.getFirstName(), contact);
    }

    public Contact findContactByPhonenumber(String phonenumberStr) {
        return contacts
                .values()
                .stream()
                .filter(contact -> contact
                        .getPhones()
                        .values()
                        .stream()
                        .allMatch(phoneNumber -> phoneNumber
                                .getNumber()
                                .equals(phonenumberStr)
                        )
                )
                .findAny()
                .orElse(null);
    }
}
