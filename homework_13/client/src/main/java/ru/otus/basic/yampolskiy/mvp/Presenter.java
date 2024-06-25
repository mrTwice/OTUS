package ru.otus.basic.yampolskiy.mvp;


import ru.otus.basic.yampolskiy.protocol.Message;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Presenter {
    private boolean isRegistered;
    private boolean isLogined;
    private final BlockingQueue<Message> messages;
    private final View view;
    private final Model model;

    public boolean isRegistered() {
        return isRegistered;
    }

    public void setRegistered(boolean registered) {
        isRegistered = registered;
    }

    public boolean isLogined() {
        return isLogined;
    }

    public void setLogined(boolean logined) {
        isLogined = logined;
    }

    public Presenter() {
        messages = new LinkedBlockingQueue<>();
        this.view = new View();
        this.model = new Model(this, messages);
    }

    public void start(){

    }
}
