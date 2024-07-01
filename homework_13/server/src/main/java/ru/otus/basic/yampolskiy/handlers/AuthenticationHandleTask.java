package ru.otus.basic.yampolskiy.handlers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.basic.yampolskiy.entities.Client;
import ru.otus.basic.yampolskiy.entities.User;
import ru.otus.basic.yampolskiy.exception.UserNotFoundException;
import ru.otus.basic.yampolskiy.protocol.Command;
import ru.otus.basic.yampolskiy.protocol.Parcel;
import ru.otus.basic.yampolskiy.protocol.dto.UserLoginDTO;
import ru.otus.basic.yampolskiy.service.UserService;
import ru.otus.basic.yampolskiy.service.UserServiceImpl;
import ru.otus.basic.yampolskiy.utils.ObjectMapperSingleton;

import java.io.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class AuthenticationHandleTask implements Runnable, Task {
    private final BlockingQueue<Client> newClients;
    private final BlockingQueue<Client> authenticationQueue;
    private final BlockingQueue<Client> authorizedClients;
    private final Logger logger = LogManager.getLogger(AuthenticationHandleTask.class);
    private final ObjectMapper objectMapper = ObjectMapperSingleton.getINSTANCE();
    private final UserService userService;

    public AuthenticationHandleTask(BlockingQueue<Client> newClients, BlockingQueue<Client> authenticationQueue, BlockingQueue<Client> authorizedClients) {
        this.newClients = newClients;
        this.authenticationQueue = authenticationQueue;
        this.authorizedClients = authorizedClients;
        this.userService = UserServiceImpl.getUserService();
    }

    @Override
    public void run() {
//        while (!Thread.currentThread().isInterrupted()) {
            Client client;
            try {
                client = authenticationQueue.poll(1, TimeUnit.SECONDS);
                if(client != null){
                    logger.info("Получен клиент для авторизации");
                    try {
                        Parcel<UserLoginDTO> parcel = objectMapper.readValue(client.getCachedData(), new TypeReference<Parcel<UserLoginDTO>>() {});
                        UserLoginDTO unknownUser = parcel.getPayload();
                        logger.info("Получен новый клиент {}",unknownUser);
                        User user = authorizationUser(unknownUser);
                        if(user != null){
                            client.setUser(user);
                            client.setAuthorized(true);
                            Parcel<String> authorizedUser = new Parcel<>(Command.LOGIN_SUCCESSFUL, user.getNickname());
                            String json = objectMapper.writeValueAsString(authorizedUser);
                            client.getOut().writeUTF(json);
                            client.getOut().flush();
                            authorizedClients.put(client);
                            logger.info("Клиент");
                        }
                    } catch (Exception e) {
                        logger.error("Ошибка при обработке запроса аутентификации", e);
                        Parcel<String> unknownUser = new Parcel<>(Command.REGISTER_UNSUCCESSFUL, "Ошибка при обработке запроса аутентификации" + e);
                        String json = objectMapper.writeValueAsString(unknownUser);
                        client.getOut().writeUTF(json);
                        client.getOut().flush();
                        newClients.put(client);
                    }
                }
            } catch (InterruptedException | IOException e) {
                logger.error("Ошибка получения клинета из очереди аутентификации");
            }
//        }
    }

    private User authorizationUser(UserLoginDTO userLoginDTO) throws UserNotFoundException {
        User user = userService.getUserByEmail(userLoginDTO.getEmail());
        if(userLoginDTO.getPassword().equals(user.getPassword()))
            return user;
        return null;
    }
}
