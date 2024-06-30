package ru.otus.basic.yampolskiy.protocol;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;

@JsonSerialize
@JsonDeserialize
public class PrivateMessage extends Message{

    private String recipient;

    public PrivateMessage() {
    }

    public PrivateMessage(String messageId, String sender, String message, LocalDateTime timestamp, String recipient) {
        super(messageId, sender, message, timestamp);
        this.recipient = recipient;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
}
