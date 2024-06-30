package ru.otus.basic.yampolskiy.handlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.basic.yampolskiy.entities.Client;
import ru.otus.basic.yampolskiy.protocol.Message;
import ru.otus.basic.yampolskiy.protocol.dto.UserLoginDTO;
import ru.otus.basic.yampolskiy.service.UserService;
import ru.otus.basic.yampolskiy.service.UserServiceImpl;

import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class AuthenticationHandler implements Runnable, Handler {
    private final BlockingQueue<Client<UserLoginDTO>> authenticationQueue;
    private final BlockingQueue<Client<Message>> messagesQueue;
    private static final Logger logger = LogManager.getLogger(AuthenticationHandler.class);
    private final UserService userService;

    public AuthenticationHandler(BlockingQueue<Client<UserLoginDTO>> authenticationQueue, BlockingQueue<Client<Message>> messagesQueue) {
        this.authenticationQueue = authenticationQueue;
        this.messagesQueue = messagesQueue;
        this.userService = UserServiceImpl.getUserService();
    }

    @Override
    public void run() {
        while (true) {
            Client<?> client = authenticationQueue.poll();
            try {
                //TODO: логика обработки авторизации
            } catch (Exception e) {
                logger.error("Ошибка при обработке запроса авторизации", e);
            }
        }
    }
}
