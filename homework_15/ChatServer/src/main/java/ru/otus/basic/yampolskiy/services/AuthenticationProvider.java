package ru.otus.basic.yampolskiy.services;

import ru.otus.basic.yampolskiy.handlers.ClientHandler;

public interface AuthenticationProvider {
    void initialize();
    boolean authenticate(ClientHandler clientHandler, String login, String password);
    boolean registration(ClientHandler clientHandler, String login, String password, String username);
    boolean permissionsGranted(ClientHandler clientHandler);
}
