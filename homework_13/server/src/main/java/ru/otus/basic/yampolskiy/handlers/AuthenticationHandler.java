package ru.otus.basic.yampolskiy.handlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.basic.yampolskiy.entities.Client;
import ru.otus.basic.yampolskiy.service.UserService;
import ru.otus.basic.yampolskiy.service.UserServiceImpl;

import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class AuthenticationHandler implements Runnable, Handler {
    private final BlockingQueue<Socket> authenticationQueue;
    private final BlockingQueue<Client> messagesQueue;
    private static final Logger logger = LogManager.getLogger(AuthenticationHandler.class);
    private final UserService userService;

    public AuthenticationHandler(BlockingQueue<Socket> authenticationQueue, BlockingQueue<Client> messagesQueue) {
        this.authenticationQueue = authenticationQueue;
        this.messagesQueue = messagesQueue;
        this.userService = UserServiceImpl.getUserService();
    }

    @Override
    public void run() {
        while (true) {
            try(Socket client = authenticationQueue.take();) {
                //TODO: логика обработки авторизации
            } catch (Exception e) {
                logger.error("Ошибка при обработке запроса авторизации", e);
            }
        }
    }
}
