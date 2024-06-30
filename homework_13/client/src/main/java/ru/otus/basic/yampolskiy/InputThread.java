package ru.otus.basic.yampolskiy;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.basic.yampolskiy.protocol.Command;
import ru.otus.basic.yampolskiy.protocol.Parcel;
import ru.otus.basic.yampolskiy.protocol.dto.UserRegistrationDTO;
import ru.otus.basic.yampolskiy.utils.ObjectMapperSingleton;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class InputThread extends Thread {
    private final String USERNAME;
    private final String EMAIL;
    private final String PASSWORD;
    private boolean isRegistred = false;
    private final Scanner input = new Scanner(System.in);
    private static final Logger logger = LogManager.getLogger(InputThread.class);
    private final BlockingQueue<Parcel<?>> parcels;
    private final Socket socket;
    private final ObjectMapper objectMapper = ObjectMapperSingleton.getINSTANCE();
    private DataInputStream in;

    public InputThread(Socket socket, BlockingQueue<Parcel<?>> parcels) {
        this.socket = socket;
        this.parcels = parcels;
        try {
            this.in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            logger.info("Получен входящий поток");
        } catch (IOException e) {
            logger.error("Ошибка получения входящего потока", e);
        }
        System.out.print("Введите имя пользователя: ");
        USERNAME = input.next();
        System.out.print("Введите адрес электронной почты: ");
        EMAIL = input.next();
        System.out.print("Введите пароль: ");
        PASSWORD = input.next();
    }

    @Override
    public void run() {
        logger.info("Поток для чтения данных запущен");
        try {
            while (!Thread.currentThread().isInterrupted() && !socket.isClosed()) {
                if (in.available() > 0) {
                    processIncomingData();
                }
            }
        } catch (IOException e) {
            logger.error("Ошибка ввода-вывода при чтении данных", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Восстанавливаем статус прерывания
            logger.error("Поток был прерван", e);
        } finally {
            closeSocket();
            input.close();
        }
    }

    private void processIncomingData() throws IOException, InterruptedException {
        String json = in.readUTF();
        logger.info("Входящий json: {}", json);
        Parcel<String> parcel = objectMapper.readValue(json, new TypeReference<>() {});
        Command command = parcel.getCommand();
        String payload = parcel.getPayload();
        logger.info("Command: {}, payload: {}\n", command, payload);
        if (command.equals(Command.IDENTIFICATION)) {
            UserRegistrationDTO newUser = new UserRegistrationDTO(USERNAME, EMAIL, PASSWORD);
            Parcel<UserRegistrationDTO> registration = new Parcel<>(Command.REGISTER, newUser);
            parcels.put(registration);
        }

        if(command.equals(Command.REGISTER_COMPLETE)){
            logger.info("Вы успешно зарегистрированы.");
        }
    }

    private void closeSocket() {
        try {
            socket.close();
            logger.info("Сокет закрыт в потоке чтения данных");
        } catch (IOException e) {
            logger.error("Ошибка при закрытии сокета", e);
        }
    }
}
