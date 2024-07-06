package ru.otus.basic.yampolskiy.protocol;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

public class Chat {
    private final String id;
    private final String adminEmail;
    private final Set<String> contacts = new CopyOnWriteArraySet<>();

    public Chat(String adminEmail) {
        this.id = UUID.randomUUID().toString();
        this.adminEmail = adminEmail;
    }

    public String getId() {
        return id;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public Set<String> getContacts() {
        return contacts;
    }

    public void addUser(String userEmail) {
        contacts.add(userEmail);
    }

    public void  deleteUser(String userEmail){
        contacts.remove(userEmail);
    }
}
