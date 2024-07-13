package ru.otus.basic.yampolskiy;

public class ServerApplication {
    public static void main(String[] args) throws InterruptedException {
        DockerComposeLauncher.start();
        Thread.sleep(10000);
        new Server(8189).start();
    }
}
