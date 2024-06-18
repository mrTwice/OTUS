package ru.otus.basic.yampolskiy.handlers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.basic.yampolskiy.entities.Client;
import ru.otus.basic.yampolskiy.protocol.Command;
import ru.otus.basic.yampolskiy.protocol.Parcel;
import ru.otus.basic.yampolskiy.protocol.dto.UserLoginDTO;
import ru.otus.basic.yampolskiy.protocol.dto.UserRegistrationDTO;
import ru.otus.basic.yampolskiy.utils.ObjectMapperSingleton;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class ConnectionHandler implements Runnable, Handler {
    private final BlockingQueue<Socket> newConnections;
    private final BlockingQueue<Client<?>> registration;
    private final BlockingQueue<Client<?>> authentication;
    private static final Logger logger = LogManager.getLogger(ConnectionHandler.class);
    private static final ObjectMapper objectMapper = ObjectMapperSingleton.getINSTANCE();

    public ConnectionHandler(BlockingQueue<Socket> newConnections,
                             BlockingQueue<Client<?>> registration,
                             BlockingQueue<Client<?>> authentication) {
        this.newConnections = newConnections;
        this.registration = registration;
        this.authentication = authentication;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            Socket socket = null;
            try {
                socket = newConnections.poll(1, TimeUnit.SECONDS);
                if (socket == null) {
                    continue;
                }

                processConnection(socket);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.warn("Поток был прерван", e);
            } catch (Exception e) {
                logger.error("Ошибка при обработке нового подключения", e);
            }
        }
    }

    private void processConnection(Socket socket) throws IOException, InterruptedException {
        logger.info("Принято новое подключение от: {}", socket.getRemoteSocketAddress());

        try (DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
             DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()))) {

            // Отправляем команду IDENTIFICATION клиенту
            Parcel<String> greetings = new Parcel<>(Command.IDENTIFICATION, "Добро пожаловать! Вы подключились к чат-серверу.");
            out.writeUTF(objectMapper.writeValueAsString(greetings));
            out.flush();
            logger.info("Отправлено приветствие клиенту: {}", socket.getRemoteSocketAddress());

            long startTime = System.currentTimeMillis();
            long timeout = 10000; // Тайм-аут 10 секунд

            // Ожидаем ответ от клиента
            while (System.currentTimeMillis() - startTime < timeout) {
                if (in.available() > 0) {
                    String responseJson = in.readUTF();
                    logger.info("Получен ответ от клиента: {}", responseJson);
                    Parcel<?> responseParcel = objectMapper.readValue(responseJson, new TypeReference<Parcel<?>>() {});

                    Command command = responseParcel.getCommand();
                    processCommandFromClient(command, responseJson, socket);
                    break;
                } else {
                    Thread.sleep(100);
                }
            }

            if (System.currentTimeMillis() - startTime >= timeout) {
                logger.warn("Тайм-аут ожидания ответа от клиента: {}", socket.getRemoteSocketAddress());
                newConnections.put(socket);
            }
        }
    }

    private void processCommandFromClient(Command command, String responseJson, Socket socket) throws InterruptedException {
        try {
            if (command.equals(Command.REGISTER)) {
                // Десериализация JSON в UserRegistrationDTO
                Parcel<UserRegistrationDTO> registrationParcel = objectMapper.readValue(responseJson, new TypeReference<Parcel<UserRegistrationDTO>>() {});
                Client<UserRegistrationDTO> client = new Client<>(socket);
                client.setCachedData(registrationParcel.getPayload());
                registration.put(client);
                logger.info("Клиент добавлен в очередь регистрации: {}", socket.getRemoteSocketAddress());
            } else if (command.equals(Command.LOGIN)) {
                // Десериализация JSON в UserLoginDTO
                Parcel<UserLoginDTO> loginParcel = objectMapper.readValue(responseJson, new TypeReference<Parcel<UserLoginDTO>>() {});
                Client<UserLoginDTO> client = new Client<>(socket);
                client.setCachedData(loginParcel.getPayload());
                authentication.put(client);
                logger.info("Клиент добавлен в очередь аутентификации: {}", socket.getRemoteSocketAddress());
            }
        } catch (IOException e) {
            logger.error("Ошибка десериализации ответа клиента: {}", socket.getRemoteSocketAddress(), e);
        }
    }

}

