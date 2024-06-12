package ru.otus.basic.yampolskiy;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.Socket;

public class ChatClient {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 9090;
    private static final Logger logger = LogManager.getLogger(ChatClient.class);

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PORT)){
            logger.info("Установлено соединение с сервером {}:{}", socket.getInetAddress(), socket.getPort());

            InputThread inputThread = new InputThread(socket);
            OutputThread outputThread = new OutputThread(socket);

            inputThread.start();
            outputThread.start();

            inputThread.join();
            outputThread.join();


        } catch (Exception e) {
            logger.error("Критическая ошибка");
            logger.throwing(Level.FATAL, e);
        }
    }
}
