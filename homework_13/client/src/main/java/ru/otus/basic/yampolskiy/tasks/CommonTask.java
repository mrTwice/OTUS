package ru.otus.basic.yampolskiy.tasks;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.basic.yampolskiy.controllers.CommonController;
import ru.otus.basic.yampolskiy.protocol.Command;
import ru.otus.basic.yampolskiy.protocol.Information;
import ru.otus.basic.yampolskiy.protocol.Message;
import ru.otus.basic.yampolskiy.protocol.Parcel;
import ru.otus.basic.yampolskiy.protocol.dto.UserAuthorizedDTO;
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
        while (true) {
            String rawParcel = incoming.poll();
            if(rawParcel != null) {
                try {
                    Parcel<?> parcel = objectMapper.readValue(rawParcel, new TypeReference<Parcel<?>>() {
                    });
                    processParcel(parcel);
                } catch (JsonProcessingException | InterruptedException e) {
                    logger.error("Ошибка десериализации объекта ");
                }
            }
        }
    }

    private void updateRegisteredStatus(boolean status) {
        if(!commonController.isRegistered()) {
            commonController.setRegisteredStatus(status);
        }
    }

    private void updateLoginStatus(boolean status) {
        if(!commonController.isLogined()) {
            commonController.setLoginStatus(status);

        }
    }

    private void processParcel(Parcel<?> parcel) throws InterruptedException {
        Command command = parcel.getCommand();
        switch (command){
            case Command.REGISTER_SUCCESSFUL-> {
                String message =(String) parcel.getPayload();
                updateRegisteredStatus(true);
            }
            case Command.REGISTER_UNSUCCESSFUL -> updateRegisteredStatus(false);
            case Command.LOGIN_SUCCESSFUL -> {
                Parcel<UserAuthorizedDTO> parcelMessage = objectMapper.convertValue(parcel, new TypeReference<Parcel<UserAuthorizedDTO>>() {});
                UserAuthorizedDTO userAuthorizedDTO = parcelMessage.getPayload();
                commonController.setAuthorizedUser(userAuthorizedDTO);
                updateLoginStatus(true);
            }
            case Command.LOGIN_UNSUCCESSFUL -> updateLoginStatus(false);
            case Command.MESSAGE ->{
                Parcel<Message> parcelMessage = objectMapper.convertValue(parcel, new TypeReference<Parcel<Message>>() {});
                Message message = parcelMessage.getPayload();
                messages.put(message);
                commonController.showMessage(message);

            }
            case Command.INFORMATION ->{
                Parcel<Information> info = objectMapper.convertValue(parcel, new TypeReference<Parcel<Information>>() {});
                Information message = info.getPayload();
                commonController.showMessage(message);

            }
            default -> logger.info("Неподдерживаемая команда {}", command);
        }
    }
}
