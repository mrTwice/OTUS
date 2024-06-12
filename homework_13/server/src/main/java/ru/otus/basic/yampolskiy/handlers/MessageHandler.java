package ru.otus.basic.yampolskiy.handlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.basic.yampolskiy.entities.Client;

import java.util.concurrent.BlockingQueue;

public class MessageHandler implements Runnable, Handler {
    private final BlockingQueue<Client> messageQueue;
    private static final Logger logger = LogManager.getLogger(MessageHandler.class);

    public MessageHandler(BlockingQueue<Client> messageQueue) {
        this.messageQueue = messageQueue;
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
