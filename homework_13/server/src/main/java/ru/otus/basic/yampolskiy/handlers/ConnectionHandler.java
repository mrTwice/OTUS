package ru.otus.basic.yampolskiy.handlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class ConnectionHandler implements Runnable, Handler {
    private final BlockingQueue<Socket> newConnections;
    private final BlockingQueue<Socket> registration;
    private final BlockingQueue<Socket> authentication;
    private static final Logger logger = LogManager.getLogger(ConnectionHandler.class);

    public ConnectionHandler(BlockingQueue<Socket> newConnections,
                             BlockingQueue<Socket> registration,
                             BlockingQueue<Socket> authentication) {
        this.newConnections = newConnections;
        this.registration = registration;
        this.authentication = authentication;
    }

    @Override
    public void run() {
        while (true) {
            try (Socket client = newConnections.take();
                 DataInputStream in = new DataInputStream(new BufferedInputStream(client.getInputStream()));
                 DataOutputStream out = new DataOutputStream(new BufferedOutputStream(client.getOutputStream()))) {
                //TODO: логика распределения между очередями
            } catch (Exception e) {
                logger.error("Ошибка при обработке нового подключения", e);
            }
        }
    }
}
