package ru.otus.basic.yampolskiy.entities;

import ru.otus.basic.yampolskiy.ChatServer;

import java.net.Socket;

public class Client<T> {
    private final ChatServer server;
    private int timeoutCount;
    private final Socket socket;
    private User user;
    private T cachedData;

    public Client(ChatServer server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public T getCachedData() {
        return cachedData;
    }

    public void setCachedData(T cachedData) {
        this.cachedData = cachedData;
    }

    public int getTimeoutCount() {
        return timeoutCount;
    }

    public void incrementTimeoutCount() {
        timeoutCount++;
    }

    public ChatServer getServer() {
        return server;
    }
}
