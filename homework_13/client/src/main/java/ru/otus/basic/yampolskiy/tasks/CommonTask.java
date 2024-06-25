package ru.otus.basic.yampolskiy.tasks;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.basic.yampolskiy.controllers.CommonController;
import ru.otus.basic.yampolskiy.protocol.Message;
import ru.otus.basic.yampolskiy.utils.ObjectMapperSingleton;

import java.util.concurrent.BlockingQueue;

public class CommonTask implements Runnable{
    private final ObjectMapper objectMapper = ObjectMapperSingleton.getINSTANCE();
    private final Logger logger = LogManager.getLogger(ReceiveTask.class);
    private final CommonController commonController;
    private final BlockingQueue<Message> messages;
    private final BlockingQueue<String> incoming;

    public CommonTask(CommonController commonController, BlockingQueue<String> incoming, BlockingQueue<Message> messages) {
        this.commonController = commonController;
        this.messages = messages;
        this.incoming = incoming;
    }

    @Override
    public void run() {


    }
}
