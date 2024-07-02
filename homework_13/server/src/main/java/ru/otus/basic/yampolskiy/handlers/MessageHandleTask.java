package ru.otus.basic.yampolskiy.handlers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.basic.yampolskiy.ChatServer;
import ru.otus.basic.yampolskiy.entities.Client;
import ru.otus.basic.yampolskiy.protocol.Command;
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
    private final Logger logger = LogManager.getLogger(MessageHandleTask.class);
    private final ObjectMapper objectMapper = ObjectMapperSingleton.getINSTANCE();

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
                if (client.getIn().available() > 0) {
                    String inputJson = client.getIn().readUTF();
                    putClient(client);
                    Parcel<Message> mesageParcel = objectMapper.readValue(inputJson, new TypeReference<Parcel<Message>>() {
                    });
                    Message message = mesageParcel.getPayload();
                    messages.put(message);
                    Parcel<Message> response = new Parcel<>(Command.MESSAGE, message);
                    String m = objectMapper.writeValueAsString(response);
                    chatServer.sendMessage(m);
                }
                putClient(client);
            }
        } catch (IOException | InterruptedException e) {
            logger.error(e);
        }
    }

    private void putClient(Client client) throws InterruptedException {
        if (!authorizedClients.contains(client)) {
            authorizedClients.put(client);
        }
    }

}
