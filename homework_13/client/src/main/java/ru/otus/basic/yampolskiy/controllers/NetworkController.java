package ru.otus.basic.yampolskiy.controllers;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.basic.yampolskiy.tasks.ReceiveTask;
import ru.otus.basic.yampolskiy.tasks.SendTask;


import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class NetworkController {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 9090;
    private final Logger logger = LogManager.getLogger(NetworkController.class);
    private final BlockingQueue<String> incoming;
    private final BlockingQueue<String> outcoming;

    public NetworkController(BlockingQueue<String> incoming, BlockingQueue<String> outcoming) {
        this.incoming = incoming;
        this.outcoming = outcoming;
    }

    public void connecting() {
        logger.info("Устанавливаем соединение с сервером {}:{}\n", HOST, PORT);
        try{
            Socket socket = new Socket(HOST, PORT);
            logger.info("Связь с сервером {}:{} установленна \n", socket.getInetAddress(), socket.getPort());

            new Thread(new ReceiveTask(this, socket, incoming)).start();
            new Thread(new SendTask(this,socket, outcoming)).start();

        } catch (Exception e) {
            logger.error("Критическая ошибка");
            logger.throwing(Level.FATAL, e);
        }
    }

}
