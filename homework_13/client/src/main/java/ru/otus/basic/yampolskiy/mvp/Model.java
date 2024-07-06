package ru.otus.basic.yampolskiy.mvp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.basic.yampolskiy.controllers.CommonController;
import ru.otus.basic.yampolskiy.controllers.LoginController;
import ru.otus.basic.yampolskiy.controllers.NetworkController;
import ru.otus.basic.yampolskiy.controllers.RegistrationController;
import ru.otus.basic.yampolskiy.protocol.Message;
import ru.otus.basic.yampolskiy.protocol.dto.UserAuthorizedDTO;
import ru.otus.basic.yampolskiy.protocol.dto.UserLoginDTO;
import ru.otus.basic.yampolskiy.protocol.dto.UserRegistrationDTO;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Model {
    private static final Logger logger = LogManager.getLogger(Model.class);
    private final Presenter presenter;
    private final BlockingQueue<Message> messages = new LinkedBlockingQueue<>();
    private final BlockingQueue<String> incoming = new LinkedBlockingQueue<>();
    private final BlockingQueue<String> outcoming  = new LinkedBlockingQueue<>();
    private final NetworkController networkController  = new NetworkController(incoming, outcoming);
    private final RegistrationController registrationController  = new RegistrationController( this);
    private final LoginController loginController  = new LoginController(this);
    private final CommonController commonController  = new CommonController(this, incoming, messages);
    private boolean isRegistered = false;
    private boolean isLogined = false;

    public Model(Presenter presenter) {
        this.presenter = presenter;
        networkController.connecting();
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

    public void setUser(UserAuthorizedDTO userAuthorizedDTO){
        presenter.setUser(userAuthorizedDTO);
    }

    public boolean sendRegisterRequest(UserRegistrationDTO newUser) {
        return registrationController.registration(newUser);
    }

    public boolean sendLoginRequest(UserLoginDTO user) {
        return loginController.authorization(user);
    }

    public void setRegistrationStatus(boolean status) {
        isRegistered= status;
    }

    public void setLoginStatus(boolean status) {
        isLogined = status;
    }

    public boolean isRegistered(){
        return isRegistered;
    }

    public boolean isLogined() {
        return isLogined;
    }

    public void showMessage(Message message){
        presenter.showNewMessage(message);
    }
}
