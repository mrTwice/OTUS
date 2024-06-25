package ru.otus.basic.yampolskiy.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.basic.yampolskiy.mvp.Model;
import ru.otus.basic.yampolskiy.protocol.Command;
import ru.otus.basic.yampolskiy.protocol.Parcel;
import ru.otus.basic.yampolskiy.protocol.dto.UserRegistrationDTO;
import ru.otus.basic.yampolskiy.utils.ObjectMapperSingleton;

public class RegistrationController {
    private static final Logger logger = LogManager.getLogger(RegistrationController.class);
    private final ObjectMapper objectMapper = ObjectMapperSingleton.getINSTANCE();
    private final Model model;

    public RegistrationController(Model model) {
        this.model = model;
    }

    public boolean registration(UserRegistrationDTO newUser) {
        Parcel<UserRegistrationDTO> userRegistrationDTOParcel = new Parcel<>(Command.REGISTER, newUser);
        try {
            String data = objectMapper.writeValueAsString(userRegistrationDTOParcel);
            model.send(data);
            return true;
        } catch (JsonProcessingException e) {
            logger.error("Ошибка сериализации объекта в процессе регистрации");
            return false;
        }
    }
}
