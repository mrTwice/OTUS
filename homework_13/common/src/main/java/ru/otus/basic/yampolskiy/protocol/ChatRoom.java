package ru.otus.basic.yampolskiy.protocol;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatRoom {
    private final String chatRoomId;
    private  final List<String> contacts = new CopyOnWriteArrayList<>();

    public ChatRoom(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public List<String> getContacts() {
        return contacts;
    }

    public String getChatRoomId() {
        return chatRoomId;
    }
}
