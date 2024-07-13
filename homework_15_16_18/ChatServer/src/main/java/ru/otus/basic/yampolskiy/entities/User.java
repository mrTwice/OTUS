package ru.otus.basic.yampolskiy.entities;

public class User {
    private String login;
    private String password;
    private String username;
    private Role role;


    public User(String login, String password, String username) {
        this.login = login;
        this.password = password;
        this.username = username;
        this.role = Role.USER;
    }

    public User(String login, String password, String username, Role role) {
        this.login = login;
        this.password = password;
        this.username = username;
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public Role getRole() {
        return role;
    }
}