package ru.otus.basic.yampolskiy.protocol;

public class Parcel<T> {
    private Command command;
    private Payload<T> payload;

    public Parcel(Command command, Payload<T> payload) {
        this.command = command;
        this.payload = payload;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public Payload<T> getPayload() {
        return payload;
    }

    public void setPayload(Payload<T> payload) {
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
