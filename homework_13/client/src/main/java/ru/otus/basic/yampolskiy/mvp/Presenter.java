package ru.otus.basic.yampolskiy.mvp;


import ru.otus.basic.yampolskiy.protocol.Message;
import ru.otus.basic.yampolskiy.protocol.dto.UserLoginDTO;
import ru.otus.basic.yampolskiy.protocol.dto.UserRegistrationDTO;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Presenter {
    private String nickname;
    private boolean isRegistered;
    private boolean isLogined;
    private final BlockingQueue<Message> messages;
    private final View view;
    private final Model model;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public void setRegistered(boolean registered) {
        isRegistered = registered;
    }

    public boolean isLogined() {
        return isLogined;
    }

    public void setLogined(boolean logined) {
        isLogined = logined;
    }

    public Presenter() {
        messages = new LinkedBlockingQueue<>();
        this.view = new View();
        this.model = new Model(this, messages);
    }

    public void start(){
        boolean stopProgram = false;
        view.printMenu();
        while (!stopProgram){
            switch (view.getOperation()) {
                case 1 -> {
                    if(!isRegistered) {
                        String newNickname = view.getInput("Введите имя: ");
                        String email = view.getInput("Введите email: ");
                        String password = view.getInput("Введите пароль: ");
                        UserRegistrationDTO newUser = new UserRegistrationDTO(newNickname, email, password);
                        model.sendRegisterRequest(newUser);
                    } else {
                        view.printMessage("Вы уже зарегистрированы.");
                    }
                }
                case 2 -> {
                    if(!isLogined) {
                        String email = view.getInput("Введите email: ");
                        String password = view.getInput("Введите пароль: ");
                        UserLoginDTO userLoginDTO = new UserLoginDTO(email, password);
                        model.sendLoginRequest(userLoginDTO);
                    }else {
                        view.printMessage("Вы уже авторизованы.");
                    }
                }
                case 3 -> {
                    if(!isLogined) {
                        view.printMessage("Вы не авторизованы");
                    }
                }
                case 4 -> stopProgram = true;
                default -> System.out.println("Такого пункта нет.");

            }
        }
    }
}
