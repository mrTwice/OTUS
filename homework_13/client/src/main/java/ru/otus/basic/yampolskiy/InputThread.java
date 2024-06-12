package ru.otus.basic.yampolskiy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class InputThread extends Thread {
    private static final Logger logger = LogManager.getLogger(InputThread.class);
    private final Socket socket;
    private DataInputStream in;

    public InputThread(Socket socket) {
        this.socket = socket;
        try {
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            logger.info("Получен входящий поток");
        } catch (IOException e) {
            logger.error("Ошибка получения входящего потока", e);
        }
    }

    @Override
    public void run() {
        logger.info("Поток для чтения данных запущен");
        try {
            while (!socket.isClosed()) {
                if (in.available() > 0) {
                    String message = in.readUTF();
                    logger.info("Получено сообщение: {}", message);
                    System.out.println(message);
                }
            }
        } catch (IOException e) {
            if (socket.isClosed()) {
                logger.info("Сокет был закрыт");
            } else {
                logger.error("Не удалось получить данные", e);
            }
        } finally {
            try {
                socket.close();
                logger.info("Сокет закрыт в потоке чтения данных");
            } catch (IOException e) {
                logger.error("Ошибка при закрытии сокета", e);
            }
        }
    }
}
