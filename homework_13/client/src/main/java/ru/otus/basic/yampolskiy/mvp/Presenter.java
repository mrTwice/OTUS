package ru.otus.basic.yampolskiy.mvp;


import com.fasterxml.jackson.core.JsonProcessingException;
import ru.otus.basic.yampolskiy.protocol.Command;
import ru.otus.basic.yampolskiy.protocol.Message;
import ru.otus.basic.yampolskiy.protocol.Parcel;
import ru.otus.basic.yampolskiy.protocol.dto.UserAuthorizedDTO;
import ru.otus.basic.yampolskiy.protocol.dto.UserLoginDTO;
import ru.otus.basic.yampolskiy.protocol.dto.UserRegistrationDTO;
import ru.otus.basic.yampolskiy.utils.ObjectMapperSingleton;

import java.time.LocalDateTime;

public class Presenter {
    private UserAuthorizedDTO user;
    private final View view;
    private final Model model;

    public void setUser(UserAuthorizedDTO user) {
        this.user = user;
    }

    public Presenter() {

        this.view = new View();
        this.model = new Model(this);
    }

    public void start() throws JsonProcessingException {
        boolean stopProgram = false;
        view.printMenu();
        while (!stopProgram) {
            switch (view.getOperation()) {
                case 1 -> {
                    if (!model.isRegistered()) {
                        String newNickname = view.getInput("Введите имя: ");
                        String email = view.getInput("Введите email: ");
                        String password = view.getInput("Введите пароль: ");
                        UserRegistrationDTO newUser = new UserRegistrationDTO(newNickname, email, password);
                        model.sendRegisterRequest(newUser);
                    } else {
                        view.printInfoMessage("Вы уже зарегистрированы.");
                    }
                    view.printMenu(); // отображение меню после регистрации
                }
                case 2 -> {
                    if (!model.isLogined()) {
                        String email = view.getInput("Введите email: ");
                        String password = view.getInput("Введите пароль: ");
                        UserLoginDTO userLoginDTO = new UserLoginDTO(email, password);
                        model.sendLoginRequest(userLoginDTO);
                    } else {
                        view.printInfoMessage("Вы уже авторизованы.");
                    }
                    view.printMenu(); // отображение меню после авторизации
                }
                case 3 -> {
                    if (model.isLogined()) {
                        chatRunning();
                    } else {
                        view.printInfoMessage("Вы не авторизованы.");
                    }
                }
                case 4 -> stopProgram = true;
                default -> System.out.println("Такого пункта нет.");
            }
        }
    }

    private void chatRunning() throws JsonProcessingException {
        while (true) {
            String input = view.getInput("");
            Message message = new Message(user.getUserId(), user.getNickname(), user.getEmail(), input, LocalDateTime.now().toString());
            Parcel<Message> parcelMessage = new Parcel<>(Command.MESSAGE, message);
            String parcel = ObjectMapperSingleton.getINSTANCE().writeValueAsString(parcelMessage);
            model.send(parcel);
        }
    }

    public void showNewMessage(Message message) {
        view.printChatMessage(message);
    }
}
