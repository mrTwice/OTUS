package ru.otus.basic.yampolskiy.handlers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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

public class AuthenticationHandler implements Runnable, Handler {
    private final BlockingQueue<Client<?>> authenticationQueue;
    private final BlockingQueue<Client<?>> messagesQueue;
    private final Logger logger = LogManager.getLogger(AuthenticationHandler.class);
    private final ObjectMapper objectMapper = ObjectMapperSingleton.getINSTANCE();
    private final UserService userService;

    public AuthenticationHandler(BlockingQueue<Client<?>> authenticationQueue, BlockingQueue<Client<?>> messagesQueue) {
        this.authenticationQueue = authenticationQueue;
        this.messagesQueue = messagesQueue;
        this.userService = UserServiceImpl.getUserService();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            Client<?> client;
            try {
                client = authenticationQueue.poll(1, TimeUnit.SECONDS);
                if(client != null){
                    logger.info("Получен клиент для авторизации");
                    try {
                        Socket currentSocket = client.getSocket();
                        DataInputStream in = new DataInputStream(new BufferedInputStream(currentSocket.getInputStream()));
                        DataOutputStream out = new DataOutputStream(new BufferedOutputStream(currentSocket.getOutputStream()));
                        //UserLoginDTO unknownUser = (UserLoginDTO) client.getCachedData();
                        Parcel<UserLoginDTO> parcel = objectMapper.readValue(in.readUTF(), new TypeReference<Parcel<UserLoginDTO>>() {});
                        UserLoginDTO unknownUser = parcel.getPayload();
                        logger.info("Получен новый клиент {}",unknownUser);
                        User user = authorizationUser(unknownUser);
                        if(user != null){
                            Parcel<String> authorizedUser = new Parcel<>(Command.LOGIN_SUCCESSFUL, user.getNickname());
                            String json = objectMapper.writeValueAsString(authorizedUser);
                            out.writeUTF(json);
                            out.flush();
                            messagesQueue.add(client);
                        }
                    } catch (Exception e) {
                        logger.error("Ошибка при обработке запроса аутентификации", e);
                    }
                }
            } catch (InterruptedException e) {
                logger.error("Ошибка получения клинета из очереди аутентификации");
            }
        }
    }

    private User authorizationUser(UserLoginDTO userLoginDTO) throws UserNotFoundException {
        User user = userService.getUserByEmail(userLoginDTO.getEmail());
        if(userLoginDTO.getPassword().equals(user.getPassword()))
            return user;
        return null;
    }
}
