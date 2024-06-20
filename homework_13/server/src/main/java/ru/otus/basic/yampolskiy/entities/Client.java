package ru.otus.basic.yampolskiy.entities;

import java.net.Socket;

public class Client<T> {
    private int timeoutCount;
    private final Socket socket;
    private User user;
    private T cachedData;

    public Client(Socket socket) {
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
}
