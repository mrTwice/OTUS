package ru.otus.basic.yampolskiy.mvp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.basic.yampolskiy.controllers.CommonController;
import ru.otus.basic.yampolskiy.controllers.LoginController;
import ru.otus.basic.yampolskiy.controllers.NetworkController;
import ru.otus.basic.yampolskiy.controllers.RegistrationController;
import ru.otus.basic.yampolskiy.protocol.Message;
import ru.otus.basic.yampolskiy.protocol.dto.UserLoginDTO;
import ru.otus.basic.yampolskiy.protocol.dto.UserRegistrationDTO;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Model {
    private static final Logger logger = LogManager.getLogger(Model.class);
    private boolean isRegistered;
    private boolean isLoggined;
    private final Presenter presenter;
    private final BlockingQueue<Message> messages;
    private final BlockingQueue<String> incoming;
    private final BlockingQueue<String> outcoming;
    private final NetworkController networkController;
    private final RegistrationController registrationController;
    private final LoginController loginController;
    private final CommonController commonController;

    public Model(Presenter presenter, BlockingQueue<Message> messages) {
        this.presenter = presenter;
        this.messages = messages;
        incoming = new LinkedBlockingQueue<>();
        outcoming = new LinkedBlockingQueue<>();
        networkController = new NetworkController(incoming, outcoming);
        networkController.connecting();
        registrationController = new RegistrationController( this);
        loginController = new LoginController(this);
        commonController = new CommonController(this, incoming, messages);
        commonController.processing();
    }

    public boolean send (String parcel) {
        try {
            outcoming.put(parcel);
            return true;
        } catch (InterruptedException e) {
            logger.error("Сообщение не было отправлено {}.", e.getMessage());
            return false;
        }
    }

    public void setNickname(String nickname){
        presenter.setNickname(nickname);
    }

    public boolean sendRegisterRequest(UserRegistrationDTO newUser) {
        return registrationController.registration(newUser);
    }

    public boolean sendLoginRequest(UserLoginDTO user) {
        return loginController.authorization(user);
    }

    public void setRegistrationStatus(boolean status) {
        presenter.setRegistered(status);
    }

    public void setLoginStatus(boolean status) {
        presenter.setLogined(status);
    }

    public boolean isRegistered(){
        return presenter.isRegistered();
    }

    public boolean isLoggined() {
        return presenter.isLogined();
    }
}
