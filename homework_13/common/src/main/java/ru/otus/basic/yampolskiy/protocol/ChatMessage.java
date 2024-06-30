package ru.otus.basic.yampolskiy.protocol;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;

@JsonSerialize
@JsonDeserialize
public class ChatMessage extends Message{

    private int chatId;

    public ChatMessage() {
    }

    public ChatMessage(String messageId, String sender, String message, LocalDateTime timestamp, int chatId) {
        super(messageId, sender, message, timestamp);
        this.chatId = chatId;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }
}
