package ru.otus.basic.yampolskiy.tasks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.basic.yampolskiy.controllers.NetworkController;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class SendTask implements Runnable {
    private final NetworkController client;
    private final Logger logger = LogManager.getLogger(SendTask.class);
    private final BlockingQueue<String> outcoming;
    private final Socket socket;
    private DataOutputStream out;

    public SendTask(NetworkController client, Socket socket, BlockingQueue<String> outcoming) throws IOException {
        this.client = client;
        this.socket = socket;
        this.outcoming = outcoming;
        this.out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
    }

    @Override
    public void run() {
        logger.info("Процесс отправки сообщений запущен");
        try {
            while (!socket.isClosed()) {
                String parcel = outcoming.poll();
                if(parcel != null){
                    out.writeUTF(parcel);
                    out.flush();
                    logger.info("Отправлен пакет: {}", parcel);
                }
            }
        } catch (IOException e) {
            if (socket.isClosed()) {
                logger.info("Сокет был закрыт");
            } else {
                logger.error("Критическая ошибка", e);
            }
        }
    }
}
