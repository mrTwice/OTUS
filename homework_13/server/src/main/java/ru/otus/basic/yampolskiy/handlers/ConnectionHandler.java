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
    private final int ATTEMPT = 5;
    private final BlockingQueue<Client<?>> connections;
    private final BlockingQueue<Client<?>> registration;
    private final BlockingQueue<Client<?>> authentication;
    private static final Logger logger = LogManager.getLogger(ConnectionHandler.class);
    private static final ObjectMapper objectMapper = ObjectMapperSingleton.getINSTANCE();

    public ConnectionHandler(BlockingQueue<Client<?>> connections,
                             BlockingQueue<Client<?>> registration,
                             BlockingQueue<Client<?>> authentication) {
        this.connections = connections;
        this.registration = registration;
        this.authentication = authentication;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            Client<?> client = null;
            try {
                client = connections.poll(1, TimeUnit.SECONDS);
                if (client == null) {
                    continue;
                }
                processConnection(client);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.warn("Поток был прерван", e);
            } catch (Exception e) {
                logger.error("Ошибка при обработке нового подключения", e);
            }
        }
    }

    private void processConnection(Client<?> client) throws IOException, InterruptedException {
        if(client.getTimeoutCount() > ATTEMPT) {
            logger.info("Превышено количество попыток ожидания ответа от клиента: {}", client.getTimeoutCount());
            closeResources(client.getSocket().getInputStream(), client.getSocket().getOutputStream(), client.getSocket());
            return;
        }
        Socket socket = client.getSocket();
        logger.info("Подключение получено из очереди: {}", socket.getRemoteSocketAddress());

        DataInputStream in = null;
        DataOutputStream out = null;

        try {
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
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
                    processCommandFromClient(command, responseJson, client);
                    break;
                }
            }

            if (System.currentTimeMillis() - startTime >= timeout) {
                logger.warn("Тайм-аут ожидания ответа от клиента: {}", socket.getRemoteSocketAddress());
                client.incrementTimeoutCount();
                connections.put(client);
            }
        } catch (IOException e) {
            logger.error("Ошибка обработки соединения: {}", socket.getRemoteSocketAddress());
        } catch (InterruptedException e) {
            logger.error("Выполнение задачи было прервано", e);
        }
    }

    private void processCommandFromClient(Command command, String responseJson, Client<?> rawClient) throws InterruptedException {
        try {
            if (command.equals(Command.REGISTER)) {
                // Десериализация JSON в UserRegistrationDTO
                Parcel<UserRegistrationDTO> registrationParcel = objectMapper.readValue(responseJson, new TypeReference<Parcel<UserRegistrationDTO>>() {});
                Client<UserRegistrationDTO> client;
                if(rawClient.getCachedData()==null) {
                    client = castClient(rawClient, UserRegistrationDTO.class);
                    if (client != null) {
                        client.setCachedData(registrationParcel.getPayload());
                        registration.put(client);
                        logger.info("Клиент добавлен в очередь регистрации: {}", client.getSocket().getRemoteSocketAddress());
                    }
                }

            } else if (command.equals(Command.LOGIN)) {
                // Десериализация JSON в UserLoginDTO
                Parcel<UserLoginDTO> loginParcel = objectMapper.readValue(responseJson, new TypeReference<Parcel<UserLoginDTO>>() {});
                Client<UserLoginDTO> client;
                if(rawClient.getCachedData()==null) {
                    client = castClient(rawClient, UserLoginDTO.class);
                    if (client != null) {
                        client.setCachedData(loginParcel.getPayload());
                        authentication.put(client);
                        logger.info("Клиент добавлен в очередь аутентификации: {}", client.getSocket().getRemoteSocketAddress());
                    }
                }

            }
        } catch (IOException e) {
            logger.error("Ошибка десериализации ответа клиента: {}", rawClient.getSocket().getRemoteSocketAddress(), e);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> Client<T> castClient(Client<?> rawClient, Class<T> type) {
        try {
            return (Client<T>) rawClient;
        } catch (ClassCastException e) {
            logger.error("Ошибка приведения типа клиента. Ожидался тип: {}, но найден тип: {}", type.getName(), rawClient.getCachedData().getClass().getName(), e);
            return null;
        }
    }

    private void closeResources(InputStream in, OutputStream out, Socket socket) {
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                logger.error("Ошибка при закрытии DataInputStream", e);
            }
        }
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                logger.error("Ошибка при закрытии DataOutputStream", e);
            }
        }
        if (socket != null && !socket.isClosed()) {
            try {
                socket.close();
                logger.info("Сокет клиента закрыт: {}", socket.getRemoteSocketAddress());
            } catch (IOException e) {
                logger.error("Ошибка при закрытии сокета", e);
            }
        }
    }

}

