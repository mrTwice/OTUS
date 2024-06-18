package ru.otus.basic.yampolskiy;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.basic.yampolskiy.protocol.Parcel;

import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ChatClient {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 9090;
    private static final Logger logger = LogManager.getLogger(ChatClient.class);
    private static final BlockingQueue<Parcel<?>> parcels = new LinkedBlockingQueue<>();

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PORT)){
            logger.info("Установлено соединение с сервером {}:{}", socket.getInetAddress(), socket.getPort());

            InputThread inputThread = new InputThread(socket, parcels);
            OutputThread outputThread = new OutputThread(socket, parcels);

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
