package ru.otus.basic.yampolskiy.protocol;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonDeserialize
public class Parcel<T> {
    private Command command;
    private T payload;

    public Parcel() {
    }

    public Parcel(Command command, T payload) {
        this.command = command;
        this.payload = payload;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "Parcel{" +
                "command=" + command +
                ", payload=" + payload +
                '}';
    }
}
