package ru.otus.basic.yampolskiy.protocol;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonDeserialize
public class Information extends Message{

    public Information(){

    }

    public Information(String senderId, String nickname, String email, String message, String timestamp) {
        super(senderId, nickname, email, message, timestamp);
    }

}
