package ru.otus.basic.yampolskiy.tasks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.basic.yampolskiy.controllers.NetworkController;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class ReceiveTask implements Runnable {
    private final NetworkController client;
    private final Socket socket;
    private final Logger logger = LogManager.getLogger(ReceiveTask.class);
    private final BlockingQueue<String> incoming;
    private DataInputStream in;

    public ReceiveTask(NetworkController client, Socket socket, BlockingQueue<String> incoming) throws IOException {
        this.client =client;
        this.socket = socket;
        this.incoming = incoming;
        this.in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        logger.info("Получен входящий поток");

    }

    @Override
    public void run() {
        logger.info("Процесс получения входящих сообщений запущен");
        try {
            while (!Thread.currentThread().isInterrupted() && !socket.isClosed()) {
                if (in.available() > 0) {
                    String parcel = in.readUTF();
                    logger.info("Получен пакет: {}", parcel);
                    incoming.put(parcel);
                }
            }
        } catch (IOException e) {
            logger.error("Ошибка ввода-вывода при чтении данных", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Восстанавливаем статус прерывания
            logger.error("Поток был прерван", e);
        }
    }
}
