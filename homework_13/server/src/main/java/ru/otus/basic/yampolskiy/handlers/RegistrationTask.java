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

import java.io.*;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class RegistrationTask implements Runnable, Task {
    private final BlockingQueue<Client> newClients;
    private final BlockingQueue<Client> registrationQueue;
    private final Logger logger = LogManager.getLogger(RegistrationTask.class);
    private final ObjectMapper objectMapper = ObjectMapperSingleton.getINSTANCE();
    private final UserService userService;

    public RegistrationTask(BlockingQueue<Client> newClients, BlockingQueue<Client> registrationQueue) {
        this.newClients = newClients;
        this.registrationQueue = registrationQueue;
        this.userService = UserServiceImpl.getUserService();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            Client client;
            try {
                client = registrationQueue.poll(1, TimeUnit.SECONDS);
                if(client != null){
                    logger.info("Получен клиент для регистрации");
                    try {
                        Parcel<UserRegistrationDTO> parcel = objectMapper.readValue(client.getCachedData(), new TypeReference<Parcel<UserRegistrationDTO>>() {});
                        UserRegistrationDTO newUser = parcel.getPayload();
                        logger.info("Получен новый клиент для регистрации {}",newUser);
                        registrationNewUser(newUser);
                        Parcel<String> registeredUser = new Parcel<>(Command.REGISTER_SUCCESSFUL, "Регистрация успешно пройдена");
                        String json = objectMapper.writeValueAsString(registeredUser);
                        client.getOut().writeUTF(json);
                        client.getOut().flush();
                        client.setCachedData(null);
                        newClients.put(client);
                    } catch (Exception e) {
                        logger.error("Ошибка при обработке запроса регистрации", e);
                        Parcel<String> registeredUser = new Parcel<>(Command.REGISTER_UNSUCCESSFUL, "Ошибка при обработке запроса регистрации" + e);
                        String json = objectMapper.writeValueAsString(registeredUser);
                        client.getOut().writeUTF(json);
                        client.getOut().flush();
                        newClients.put(client);
                    }
                }
            } catch (InterruptedException | IOException e) {
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
