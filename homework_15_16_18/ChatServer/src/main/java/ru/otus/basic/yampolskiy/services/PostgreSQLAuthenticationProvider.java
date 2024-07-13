package ru.otus.basic.yampolskiy.services;

import ru.otus.basic.yampolskiy.handlers.ClientHandler;
import ru.otus.basic.yampolskiy.Server;
import ru.otus.basic.yampolskiy.entities.Role;
import ru.otus.basic.yampolskiy.entities.User;
import ru.otus.basic.yampolskiy.repositories.UserRepository;

public class PostgreSQLAuthenticationProvider implements AuthenticationProvider {
    private Server server;
    private final UserRepository userRepository = new UserRepository();

    public PostgreSQLAuthenticationProvider(Server server) {
        this.server = server;
    }

    @Override
    public void initialize() {
        System.out.println("Сервис аутентификации запущен: Database режим");
    }

    private String getUsernameByLoginAndPassword(String login, String password) {
        return userRepository.getUsernameByLoginAndPassword(login, password);
    }

    private boolean isLoginAlreadyExist(String login) {
        return userRepository.isLoginAlreadyExist(login);
    }

    private boolean isUsernameAlreadyExist(String username) {
        return userRepository.isUsernameAlreadyExist(username);
    }

    private boolean isAdmin(String currentUsername){
        User user = userRepository.getUserByUsername(currentUsername);
        return user.getRole().equals(Role.ADMIN);
    }

    public synchronized boolean permissionsGranted(ClientHandler clientHandler) {
        return isAdmin(clientHandler.getUsername());
    }

    @Override
    public synchronized boolean authenticate(ClientHandler clientHandler, String login, String password) {
        String authUsername = getUsernameByLoginAndPassword(login, password);
        if (authUsername == null) {
            clientHandler.sendMessage("Некорретный логин/пароль");
            return false;
        }
        if (server.isUsernameBusy(authUsername)) {
            clientHandler.sendMessage("Указанная учетная запись уже занята");
            return false;
        }
        clientHandler.setUsername(authUsername);
        server.subscribe(clientHandler);
        clientHandler.sendMessage("/authok " + authUsername);
        return true;
    }

    @Override
    public boolean registration(ClientHandler clientHandler, String login, String password, String username) {
        if (login.trim().length() < 3 || password.trim().length() < 6 || username.trim().length() < 1) {
            clientHandler.sendMessage("Логин 3+ символа, Пароль 6+ символов, Имя пользователя 1+ символ");
            return false;
        }
        if (isLoginAlreadyExist(login)) {
            clientHandler.sendMessage("Указанный логин уже занят");
            return false;
        }
        if (isUsernameAlreadyExist(username)) {
            clientHandler.sendMessage("Указанное имя пользователя уже занято");
            return false;
        }
        userRepository.createUser(login, password, username);
        clientHandler.setUsername(username);
        server.subscribe(clientHandler);
        clientHandler.sendMessage("/regok " + username);
        return true;
    }
}
