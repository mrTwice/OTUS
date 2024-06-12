package ru.otus.basic.yampolskiy.handlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.basic.yampolskiy.service.UserService;
import ru.otus.basic.yampolskiy.service.UserServiceImpl;

import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class RegistrationHandler implements Runnable, Handler {
    private final BlockingQueue<Socket> registrationQueue;
    private final BlockingQueue<Socket> authenticationQueue;
    private static final Logger logger = LogManager.getLogger(RegistrationHandler.class);
    private final UserService userService;

    public RegistrationHandler(BlockingQueue<Socket> registrationQueue, BlockingQueue<Socket> authenticationQueue) {
        this.registrationQueue = registrationQueue;
        this.authenticationQueue = authenticationQueue;
        this.userService = UserServiceImpl.getUserService();
    }

    @Override
    public void run() {
        while (true) {
            try(Socket client = registrationQueue.take()) {
                //TODO: логика обработки регистрации
            } catch (Exception e) {
                logger.error("Ошибка при обработке запроса регистрации", e);
            }
        }
    }
}
