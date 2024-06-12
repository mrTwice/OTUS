package ru.otus.basic.yampolskiy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class OutputThread extends Thread {
    private static final Logger logger = LogManager.getLogger(OutputThread.class);
    private final Socket socket;
    private DataOutputStream out;

    public OutputThread(Socket socket) {
        this.socket = socket;
        try {
            out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            logger.info("Получен исходящий поток");
        } catch (IOException e) {
            logger.error("Ошибка получения исходящего потока", e);
        }
    }

    @Override
    public void run() {
        try (Scanner input = new Scanner(System.in)) {

            logger.info("Поток для записи данных запущен");

            while (!socket.isClosed()) {
                if (input.hasNextLine()) {
                    String string = input.nextLine();
                    out.writeUTF(string + "\n");
                    out.flush();
                    logger.info("Отправлено сообщение: {}", string);
                }
            }
        } catch (IOException e) {
            if (socket.isClosed()) {
                logger.info("Сокет был закрыт");
            } else {
                logger.error("Критическая ошибка", e);
            }
        } finally {
            try {
                socket.close();
                logger.info("Сокет закрыт в потоке записи данных");
            } catch (IOException e) {
                logger.error("Ошибка при закрытии сокета", e);
            }
        }
    }
}
