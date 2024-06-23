package ru.otus.basic.yampolskiy.entities;

import java.util.Objects;
import java.util.UUID;

public class User {
    private UUID id;
    private String nickname;
    private String email;
    private String password;

    public User(String nickname, String email, String password) {
        this.id = UUID.randomUUID();
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id.toString();
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(nickname, user.nickname) && Objects.equals(email, user.email) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nickname, email, password);
    }
}
