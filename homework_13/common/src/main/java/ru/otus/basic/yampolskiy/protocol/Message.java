package ru.otus.basic.yampolskiy.protocol;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;

@JsonSerialize
@JsonDeserialize
public class Message<T> {
    private String messageId;
    private String sender;
    private String receiver;
    private String message;
    private T payload;
    private LocalDateTime timestamp;

    public Message() {
    }

    public Message(String messageId, String sender, String receiver, String message, T payload, LocalDateTime timestamp) {
        this.messageId = messageId;
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.payload = payload;
        this.timestamp = timestamp;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Message{" +
                "payload=" + payload +
                ", messageId='" + messageId + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
