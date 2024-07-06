package ru.otus.basic.yampolskiy;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.otus.basic.yampolskiy.mvp.Presenter;

public class ChatClient {
    public static void main(String[] args) throws JsonProcessingException {
        Presenter presenter = new Presenter();
        presenter.start();
    }
}
