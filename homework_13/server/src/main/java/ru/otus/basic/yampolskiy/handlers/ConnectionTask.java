package ru.otus.basic.yampolskiy.handlers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.basic.yampolskiy.entities.Client;
import ru.otus.basic.yampolskiy.protocol.Command;
import ru.otus.basic.yampolskiy.protocol.Parcel;
import ru.otus.basic.yampolskiy.utils.ObjectMapperSingleton;

import java.io.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class ConnectionTask implements Runnable, Task {
    private final BlockingQueue<Client> newConnections;
    private final BlockingQueue<Client> registration;
    private final BlockingQueue<Client> authentication;
    private static final Logger logger = LogManager.getLogger(ConnectionTask.class);
    private static final ObjectMapper objectMapper = ObjectMapperSingleton.getINSTANCE();

    public ConnectionTask(BlockingQueue<Client> newConnections,
                          BlockingQueue<Client> registration,
                          BlockingQueue<Client> authentication) {
        this.newConnections = newConnections;
        this.registration = registration;
        this.authentication = authentication;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Client client = newConnections.poll(1, TimeUnit.SECONDS);
                if (client == null) {
                    continue;
                }
                processing(client);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.warn("Поток был прерван", e);
            } catch (Exception e) {
                logger.error("Ошибка при обработке нового подключения", e);
            }
        }
    }

    private void processing(Client client) throws InterruptedException {
        logger.info("Получен клиент из очереди: {}", client.getSocket().getRemoteSocketAddress());

        try {
            long startTime = System.currentTimeMillis();
            long timeout = 10000; // Тайм-аут 10 секунд
            while (System.currentTimeMillis() - startTime < timeout) {
                if (client.getIn().available() > 0) {
                    String responseJson = client.getIn().readUTF();
                    logger.info("Получены данные от клиента: {}", responseJson);
                    Parcel<?> responseParcel = objectMapper.readValue(responseJson, new TypeReference<Parcel<Object>>() {});
                    Command command = responseParcel.getCommand();
                    processCommandFromClient(command, responseJson, client);
                    break;
                } else {
                    Thread.sleep(100);
                }
            }
            if (System.currentTimeMillis() - startTime >= timeout) {
                logger.warn("Тайм-аут ожидания ответа от клиента: {}", client.getSocket().getRemoteSocketAddress());
                newConnections.put(client);
            }
        } catch (IOException | InterruptedException e) {
            logger.error("Ошибка обработки клиент в очереди newConnections ");
            newConnections.put(client);
        }
    }

    private void processCommandFromClient(Command command, String data, Client client) throws InterruptedException {
            if (command.equals(Command.REGISTER)) {
                client.setCachedData(data);
                registration.put(client);
                logger.info("Клиент добавлен в очередь регистрации: {}", client.getSocket().getRemoteSocketAddress());
            } else if (command.equals(Command.LOGIN)) {
                client.setCachedData(data);
                authentication.put(client);
                logger.info("Клиент добавлен в очередь аутентификации: {}", client.getSocket().getRemoteSocketAddress());
            }
    }

}

