package ru.otus.basic.yampolskiy;

import ru.otus.basic.yampolskiy.mvp.Presenter;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        DockerComposeLauncher.start();
        Thread.sleep(10000);
        Presenter presenter = new Presenter();
        presenter.start();
    }
}