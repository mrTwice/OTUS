package ru.otus.basic.yampolskiy.handlers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.basic.yampolskiy.ChatServer;
import ru.otus.basic.yampolskiy.entities.Client;
import ru.otus.basic.yampolskiy.protocol.Message;
import ru.otus.basic.yampolskiy.protocol.Parcel;
import ru.otus.basic.yampolskiy.utils.ObjectMapperSingleton;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;


public class MessageHandleTask implements Runnable, Task {
    private final ChatServer chatServer;
    private final BlockingQueue<Client> authorizedClients;
    private final BlockingQueue<Message> messages;
    private static final Logger logger = LogManager.getLogger(MessageHandleTask.class);
    private static final ObjectMapper objectMapper = ObjectMapperSingleton.getINSTANCE();

    public MessageHandleTask(ChatServer chatServer, BlockingQueue<Client> authorizedClients, BlockingQueue<Message> messages) {
        this.chatServer = chatServer;
        this.authorizedClients = authorizedClients;
        this.messages = messages;
    }

    @Override
    public void run() {
            Client client;
            try {
                client = authorizedClients.poll(1, TimeUnit.SECONDS);
                if (client != null) {
                    logger.info("Получен следующий клиент из очереди");
                    if (client.getIn().available() > 0) {
                        String inputJson = client.getIn().readUTF();
                        putClient(client);
                        logger.info("Получены данные от клиента: {}", inputJson);
                        Parcel<Message> mesageParcel = objectMapper.readValue(inputJson, new TypeReference<Parcel<Message>>() {
                        });
                        Message message = mesageParcel.getPayload();
                        message.setSender(client.getUser().getId().toString());
                        messages.put(message);
                        chatServer.sendMessage(objectMapper.writeValueAsString(mesageParcel));
                    }
                    putClient(client);
                }
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
    }

    private void putClient(Client client) throws InterruptedException {
        if(!authorizedClients.contains(client)) {
            authorizedClients.put(client);
        }
    }

}
