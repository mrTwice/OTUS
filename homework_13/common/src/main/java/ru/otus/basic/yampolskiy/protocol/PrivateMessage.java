package ru.otus.basic.yampolskiy.protocol;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


@JsonSerialize
@JsonDeserialize
public class PrivateMessage extends Message{

    private String recipient;

    public PrivateMessage() {
    }

    public PrivateMessage(String senderId, String nickname, String email, String message, String timestamp, String recipient) {
        super(senderId, nickname, email, message, timestamp);
        this.recipient = recipient;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
}
