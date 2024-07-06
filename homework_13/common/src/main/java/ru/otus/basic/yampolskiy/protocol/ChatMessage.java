package ru.otus.basic.yampolskiy.protocol;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonDeserialize
public class ChatMessage extends Message {

    private String chatId;

    public ChatMessage() {
    }

    public ChatMessage(String senderId, String nickname, String email, String message, String timestamp, String chatId) {
        super(senderId, nickname, email, message, timestamp);
        this.chatId = chatId;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }
}
