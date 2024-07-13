package ru.otus.basic.yampolskiy.services;

import ru.otus.basic.yampolskiy.handlers.ClientHandler;
import ru.otus.basic.yampolskiy.Server;
import ru.otus.basic.yampolskiy.entities.Role;
import ru.otus.basic.yampolskiy.entities.User;

import java.util.*;

public class InMemoryAuthenticationProvider implements AuthenticationProvider {
    private final Server server;
    private final List<User> users;

    public InMemoryAuthenticationProvider(Server server) {
        this.server = server;
        this.users = new ArrayList<>();
        this.users.add(new User("admin", "admin", "admin", Role.ADMIN));
        this.users.add(new User("login1", "pass1", "bob"));
        this.users.add(new User("login2", "pass2", "user2"));
        this.users.add(new User("login3", "pass3", "user3"));
    }

    @Override
    public void initialize() {
        System.out.println("Сервис аутентификации запущен: In-Memory режим");
    }

    private String getUsernameByLoginAndPassword(String login, String password) {
        for (User u : users) {
            if (u.getLogin().equals(login) && u.getPassword().equals(password)) {
                return u.getUsername();
            }
        }
        return null;
    }

    private boolean isLoginAlreadyExist(String login) {
        for (User u : users) {
            if (u.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }

    private boolean isUsernameAlreadyExist(String username) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    private boolean isAdmin(String currentUsername){

        Optional<User> optionalUser =  users.stream()
                .filter(u -> u.getUsername().equals(currentUsername))
                .findFirst();
        User user = optionalUser.get();
        if(user != null &&  user.getRole().equals(Role.ADMIN))
            return true;
        else return false;
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
        users.add(new User(login, password, username));
        clientHandler.setUsername(username);
        server.subscribe(clientHandler);
        clientHandler.sendMessage("/regok " + username);
        return true;
    }
}