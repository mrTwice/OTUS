package ru.otus.basic.yampolskiy;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.basic.yampolskiy.protocol.Parcel;
import ru.otus.basic.yampolskiy.utils.ObjectMapperSingleton;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class OutputThread extends Thread {
    private final ObjectMapper objectMapper = ObjectMapperSingleton.getINSTANCE();
    private static final Logger logger = LogManager.getLogger(OutputThread.class);
    private final BlockingQueue<Parcel<?>> parcels;
    private final Socket socket;
    private DataOutputStream out;

    public OutputThread(Socket socket, BlockingQueue<Parcel<?>> parcels) {
        this.socket = socket;
        this.parcels = parcels;
        try {
            out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            logger.info("Получен исходящий поток");
        } catch (IOException e) {
            logger.error("Ошибка получения исходящего потока", e);
        }
    }

    @Override
    public void run() {
        logger.info("Поток для записи данных запущен");
        try {
            while (!socket.isClosed()) {
                Parcel<?> parcel = parcels.poll();
                if(parcel != null){
                    String json = objectMapper.writeValueAsString(parcel);
                    out.writeUTF(json);
                    out.flush();
                    logger.info("Отправлено сообщение: {}", json);
                }
            }
        } catch (IOException e) {
            if (socket.isClosed()) {
                logger.info("Сокет был закрыт");
            } else {
                logger.error("Критическая ошибка", e);
            }
        } finally {
            try {
                socket.close();
                logger.info("Сокет закрыт в потоке записи данных");
            } catch (IOException e) {
                logger.error("Ошибка при закрытии сокета", e);
            }
        }
    }
}
