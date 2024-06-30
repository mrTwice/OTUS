package ru.otus.basic.yampolskiy.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.basic.yampolskiy.mvp.Model;
import ru.otus.basic.yampolskiy.protocol.Command;
import ru.otus.basic.yampolskiy.protocol.Parcel;
import ru.otus.basic.yampolskiy.protocol.dto.UserLoginDTO;

import ru.otus.basic.yampolskiy.utils.ObjectMapperSingleton;

public class LoginController {
    private static final Logger logger = LogManager.getLogger(LoginController.class);
    private final ObjectMapper objectMapper = ObjectMapperSingleton.getINSTANCE();
    private final Model model;

    public LoginController(Model model) {
        this.model = model;
    }

    public boolean authorization(UserLoginDTO user) {
        if(!model.isLoggined()) {
            Parcel<UserLoginDTO> userLoginDTOParcel = new Parcel<>(Command.LOGIN, user);
            try {
                String data = objectMapper.writeValueAsString(userLoginDTOParcel);
                model.send(data);
                logger.info("Данные для авторизации отправлены: {}", data);
                return true;
            } catch (JsonProcessingException e) {
                logger.error("Ошибка сериализации объекта в процессе авторизации");
                return false;
            }
        }
        return false;
    }
}
