package ru.otus.basic.yampolskiy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.basic.yampolskiy.entities.Client;
import ru.otus.basic.yampolskiy.handlers.AuthenticationHandler;
import ru.otus.basic.yampolskiy.handlers.ConnectionHandler;
import ru.otus.basic.yampolskiy.handlers.MessageHandler;
import ru.otus.basic.yampolskiy.handlers.RegistrationHandler;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;


public class ChatServer {
    private final static int PORT = 9090;
    private static final Logger logger = LogManager.getLogger(ChatServer.class);
    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private static final BlockingQueue<Socket> newConnections = new LinkedBlockingQueue<>();
    private static final BlockingQueue<Socket> registration = new LinkedBlockingQueue<>();
    private static final BlockingQueue<Socket> authentication = new LinkedBlockingQueue<>();
    private static final BlockingQueue<Client> messages = new LinkedBlockingQueue<>();

    public static void main(String[] args) {
        try (ServerSocket chatServer = new ServerSocket(PORT)) {
            logger.info("Сервер запущен на порту: {}", PORT);

            executorService.submit(new ConnectionHandler(newConnections, registration, authentication));
            executorService.submit(new RegistrationHandler(registration, authentication));
            executorService.submit(new AuthenticationHandler(authentication, messages));
            executorService.submit(new MessageHandler(messages));

            while (true) {
                Socket newClient = chatServer.accept();
                logger.info("Запрос подключения с адреса {}:{}", newClient.getInetAddress(), newClient.getPort());
                newConnections.put(newClient);
            }
        } catch (Exception e) {
            logger.error("Критическая ошибка", e);
        }
    }
}
