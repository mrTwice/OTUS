package ru.otus.basic.yampolskiy.protocol.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonDeserialize
public class UserAuthorizedDTO {

    private String userId;
    private String nickname;
    private String email;

    public UserAuthorizedDTO() {
    }

    public UserAuthorizedDTO(String userId, String nickname, String email) {
        this.userId = userId;
        this.nickname = nickname;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }
}
