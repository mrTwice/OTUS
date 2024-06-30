package ru.otus.basic.yampolskiy.handlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.basic.yampolskiy.entities.Client;
import ru.otus.basic.yampolskiy.protocol.Message;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class MessageTask implements Runnable, Task {
    private final List<Client> authorizedClients;
    private final BlockingQueue<Message> messages;
    private static final Logger logger = LogManager.getLogger(MessageTask.class);

    public MessageTask(List<Client> authorizedClients, BlockingQueue<Message> messages) {
        this.authorizedClients = authorizedClients;
        this.messages = messages;
    }

    @Override
    public void run() {
        while (true)
            try {
                Client client = messageQueue.take();
                //TODO: логика обработки пересылаемых сообщений
            } catch (Exception e) {
                logger.error("Ошибка при обработке сообщения", e);
            }
    }

}
