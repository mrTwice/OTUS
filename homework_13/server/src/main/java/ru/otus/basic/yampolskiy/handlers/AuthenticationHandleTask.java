package ru.otus.basic.yampolskiy.handlers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.basic.yampolskiy.ChatServer;
import ru.otus.basic.yampolskiy.entities.Client;
import ru.otus.basic.yampolskiy.entities.User;
import ru.otus.basic.yampolskiy.exception.UserNotFoundException;
import ru.otus.basic.yampolskiy.protocol.Command;
import ru.otus.basic.yampolskiy.protocol.Information;
import ru.otus.basic.yampolskiy.protocol.Parcel;
import ru.otus.basic.yampolskiy.protocol.dto.UserAuthorizedDTO;
import ru.otus.basic.yampolskiy.protocol.dto.UserLoginDTO;
import ru.otus.basic.yampolskiy.service.UserService;
import ru.otus.basic.yampolskiy.service.UserServiceImpl;
import ru.otus.basic.yampolskiy.utils.ObjectMapperSingleton;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class AuthenticationHandleTask implements Runnable, Task {
    private final ChatServer server;
    private final BlockingQueue<Client> newClients;
    private final BlockingQueue<Client> authenticationQueue;
    private final BlockingQueue<Client> authorizedClients;
    private final Logger logger = LogManager.getLogger(AuthenticationHandleTask.class);
    private final ObjectMapper objectMapper = ObjectMapperSingleton.getINSTANCE();
    private final UserService userService;

    public AuthenticationHandleTask(ChatServer server, BlockingQueue<Client> newClients, BlockingQueue<Client> authenticationQueue, BlockingQueue<Client> authorizedClients) {
        this.server = server;
        this.newClients = newClients;
        this.authenticationQueue = authenticationQueue;
        this.authorizedClients = authorizedClients;
        this.userService = UserServiceImpl.getUserService();
    }

    @Override
    public void run() {
        Client client;
        try {
            client = authenticationQueue.poll(1, TimeUnit.SECONDS);
            if (client != null) {
                logger.info("Получен клиент для авторизации");
                try {
                    Parcel<UserLoginDTO> parcel = objectMapper.readValue(client.getCachedData(), new TypeReference<Parcel<UserLoginDTO>>() {
                    });
                    UserLoginDTO unknownUser = parcel.getPayload();
                    logger.info("Получен новый клиент {}", unknownUser);
                    User user = authorizationUser(unknownUser);
                    if (user != null) {
                        client.setUser(user);
                        client.setAuthorized(true);
                        UserAuthorizedDTO userAuthorizedDTO = new UserAuthorizedDTO(user.getId(), user.getNickname(), user.getEmail());
                        Parcel<UserAuthorizedDTO> authorizedUser = new Parcel<>(Command.LOGIN_SUCCESSFUL, userAuthorizedDTO);
                        String json = objectMapper.writeValueAsString(authorizedUser);
                        client.getOut().writeUTF(json);
                        client.getOut().flush();
                        notifyUsersOfNewJoin(userAuthorizedDTO);
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
    }

    private User authorizationUser(UserLoginDTO userLoginDTO) throws UserNotFoundException {
        User user = userService.getUserByEmail(userLoginDTO.getEmail());
        if (userLoginDTO.getPassword().equals(user.getPassword()))
            return user;
        return null;
    }

    private void notifyUsersOfNewJoin(UserAuthorizedDTO userAuthorizedDTO ) throws IOException {
        Information information = new Information("server", "ChatServer", "admin@email",
                "Присоеденился новый пользователь: " + userAuthorizedDTO.getNickname(), LocalDateTime.now().toString());
        Parcel<Information> info = new Parcel<>(Command.INFORMATION, information);
        String json = objectMapper.writeValueAsString(info);
        server.sendMessage(json);
    }


}
