package ru.otus.basic.yampolskiy.protocol;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatRoom {
    private  int Id;
    private  final List<String> contacts = new CopyOnWriteArrayList<>();

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public List<String> getContacts() {
        return contacts;
    }
}
