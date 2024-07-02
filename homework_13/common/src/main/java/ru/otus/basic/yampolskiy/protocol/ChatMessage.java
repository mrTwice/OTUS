package ru.otus.basic.yampolskiy.protocol;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonDeserialize
public class ChatMessage extends Message {

    private int chatId;

    public ChatMessage() {
    }

    public ChatMessage(String senderId, String nickname, String email, String message, String timestamp, int chatId) {
        super(senderId, nickname, email, message, timestamp);
        this.chatId = chatId;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }
}
