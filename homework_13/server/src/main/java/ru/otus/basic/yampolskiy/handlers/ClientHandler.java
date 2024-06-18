package ru.otus.basic.yampolskiy.handlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.util.Locale;

public class ClientHandler implements Runnable, Handler {
    private static final Logger logger = LogManager.getLogger(ClientHandler.class);
    private final Socket socket;

    public ClientHandler( Socket socket) throws IOException {
        this.socket = socket;
        logger.info("Клиент {}:{} перехвачен обработчиком", this.socket.getInetAddress(), this.socket.getPort());
    }

    @Override
    public void run() {
        try (DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
             DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()))) {
            String s = in.readUTF();
            logger.info("Получено входящее сообщение: {}", s);
            s = s.toLowerCase(Locale.ROOT);
            out.writeUTF(s); // Используем writeUTF для записи строки

        } catch (IOException e) {
            logger.error("Произошла ошибка ввода вывода", e);
        } finally {
            try {
                socket.close();
                logger.info("Сокет клиента закрыт");
            } catch (IOException e) {
                logger.error("Ошибка при закрытии сокета клиента", e);
            }
        }
    }
}
