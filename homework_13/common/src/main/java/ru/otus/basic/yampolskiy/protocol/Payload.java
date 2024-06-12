package ru.otus.basic.yampolskiy.protocol;

public class Payload<T> {
    private T payload;

    public Payload(T payload) {
        this.payload = payload;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "Payload{" +
                "payload=" + payload +
                '}';
    }
}
