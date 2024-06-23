package ru.otus.basic.yampolskiy.handlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.basic.yampolskiy.entities.Client;
import ru.otus.basic.yampolskiy.entities.User;
import ru.otus.basic.yampolskiy.exception.UserAlreadyExistException;
import ru.otus.basic.yampolskiy.exception.UserNotFoundException;
import ru.otus.basic.yampolskiy.protocol.Command;
import ru.otus.basic.yampolskiy.protocol.Parcel;
import ru.otus.basic.yampolskiy.protocol.dto.UserLoginDTO;
import ru.otus.basic.yampolskiy.protocol.dto.UserRegistrationDTO;
import ru.otus.basic.yampolskiy.service.UserService;
import ru.otus.basic.yampolskiy.service.UserServiceImpl;
import ru.otus.basic.yampolskiy.utils.ObjectMapperSingleton;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class RegistrationHandler implements Runnable, Handler {
    private final BlockingQueue<Client<UserRegistrationDTO>> registrationQueue;
    private final BlockingQueue<Client<UserLoginDTO>> authenticationQueue;
    private static final Logger logger = LogManager.getLogger(RegistrationHandler.class);
    private final UserService userService;

    public RegistrationHandler(BlockingQueue<Client<UserRegistrationDTO>> registrationQueue, BlockingQueue<Client<UserLoginDTO>> authenticationQueue) {
        this.registrationQueue = registrationQueue;
        this.authenticationQueue = authenticationQueue;
        this.userService = UserServiceImpl.getUserService();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            Client<UserRegistrationDTO> client;
            try {
                client = registrationQueue.poll(1, TimeUnit.SECONDS);
                if(client != null){
                    logger.info("Клиент получен из очереди регистрации");
                    try {
                        DataOutputStream out = new DataOutputStream(new BufferedOutputStream(client.getSocket().getOutputStream()));
                        UserRegistrationDTO newUser = client.getCachedData();
                        if(registration(newUser)){
                            logger.info("Клиент зарегистрирован");
                            Client<UserLoginDTO> registeredUser = new  Client<>(client.getServer(), client.getSocket());
                            registeredUser.setCachedData(new UserLoginDTO(newUser.getEmail(), newUser.getPassword()));
                            authenticationQueue.put(registeredUser);
                            Parcel<String> parcel = new Parcel<>(Command.REGISTER_COMPLETE, "Ok");
                            String json = ObjectMapperSingleton.getINSTANCE().writeValueAsString(parcel);
                            out.writeUTF(json);
                            out.flush();
                            logger.info("Клиент помещен в очередь для аутентификации");
                        } else {
                            registrationQueue.put(client);
                        }
                    } catch (Exception e) {
                        logger.error("Ошибка при обработке запроса регистрации", e);
                    }
                }
            } catch (InterruptedException e) {
                logger.error("Ошибка при обработке очереди регистрации", e);
            }

        }
    }

    private boolean registration(UserRegistrationDTO newUser) {
        try {
             userService.createNewUser(new User(
                        newUser.getNickname(),
                        newUser.getEmail(),
                        newUser.getPassword()
                ));

        } catch (UserAlreadyExistException e) {
            return  false;
        }
        return true;
    }
}
