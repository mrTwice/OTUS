package ru.otus.basic.yampolskiy.handlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.basic.yampolskiy.entities.Client;
import ru.otus.basic.yampolskiy.protocol.dto.UserRegistrationDTO;
import ru.otus.basic.yampolskiy.service.UserService;
import ru.otus.basic.yampolskiy.service.UserServiceImpl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class RegistrationHandler implements Runnable, Handler {
    private final BlockingQueue<Client<?>> registrationQueue;
    private final BlockingQueue<Client<?>> authenticationQueue;
    private static final Logger logger = LogManager.getLogger(RegistrationHandler.class);
    private final UserService userService;

    public RegistrationHandler(BlockingQueue<Client<?>> registrationQueue, BlockingQueue<Client<?>> authenticationQueue) {
        this.registrationQueue = registrationQueue;
        this.authenticationQueue = authenticationQueue;
        this.userService = UserServiceImpl.getUserService();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            Client<?> client;
            try {
                client = registrationQueue.poll(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(client != null){
                try ( Socket currentSocket = client.getSocket();
                      DataInputStream in = new DataInputStream(new BufferedInputStream(currentSocket.getInputStream()));
                      DataOutputStream out = new DataOutputStream(new BufferedOutputStream(currentSocket.getOutputStream()));) {
                    //TODO: логика обработки регистрации
                    UserRegistrationDTO newUser = (UserRegistrationDTO) client.getCachedData();
                    System.out.println(newUser);
                } catch (Exception e) {
                    logger.error("Ошибка при обработке запроса регистрации", e);
                }
            }
        }
    }
}
