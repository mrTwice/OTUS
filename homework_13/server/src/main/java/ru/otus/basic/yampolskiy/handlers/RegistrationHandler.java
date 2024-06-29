package ru.otus.basic.yampolskiy.handlers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.basic.yampolskiy.entities.Client;
import ru.otus.basic.yampolskiy.entities.User;
import ru.otus.basic.yampolskiy.exception.UserAlreadyExistException;
import ru.otus.basic.yampolskiy.protocol.Command;
import ru.otus.basic.yampolskiy.protocol.Parcel;
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
    private final BlockingQueue<Client<?>> registrationQueue;
    private final BlockingQueue<Client<?>> authenticationQueue;
    private final Logger logger = LogManager.getLogger(RegistrationHandler.class);
    private final ObjectMapper objectMapper = ObjectMapperSingleton.getINSTANCE();
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
                if(client != null){
                    logger.info("Получен клиент для регистрации");
                    try {
                        Socket currentSocket = client.getSocket();
                        DataInputStream in = new DataInputStream(new BufferedInputStream(currentSocket.getInputStream()));
                        DataOutputStream out = new DataOutputStream(new BufferedOutputStream(currentSocket.getOutputStream()));
                        //TODO: логика обработки регистрации
                        //UserRegistrationDTO newUser = (UserRegistrationDTO) client.getCachedData();
                        Parcel<UserRegistrationDTO> parcel = objectMapper.readValue(in.readUTF(), new TypeReference<Parcel<UserRegistrationDTO>>() {});
                        UserRegistrationDTO newUser = parcel.getPayload();
                        logger.info("Получен новый клиент {}",newUser);
                        registrationNewUser(newUser);
                        Parcel<String> registeredUser = new Parcel<>(Command.REGISTER_SUCCESSFUL, "Регистрация успешно пройдена");
                        String json = objectMapper.writeValueAsString(registeredUser);
                        out.writeUTF(json);
                        out.flush();
                        authenticationQueue.add(client);
                    } catch (Exception e) {
                        logger.error("Ошибка при обработке запроса регистрации", e);
                    }
                }
            } catch (InterruptedException e) {
                logger.error("Ошибка получения клинета из очереди регистрации");
            }
        }
    }

    private User registrationNewUser(UserRegistrationDTO userRegistrationDTO) throws UserAlreadyExistException {
        return userService.createNewUser(new User(
                UUID.randomUUID(),
                userRegistrationDTO.getNickname(),
                userRegistrationDTO.getEmail(),
                userRegistrationDTO.getPassword()
        ));
    }
}
