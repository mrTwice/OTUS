package ru.otus.basic.yampolskiy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.basic.yampolskiy.entities.Client;
import ru.otus.basic.yampolskiy.handlers.AuthenticationHandler;
import ru.otus.basic.yampolskiy.handlers.ConnectionHandler;
import ru.otus.basic.yampolskiy.handlers.MessageHandler;
import ru.otus.basic.yampolskiy.handlers.RegistrationHandler;
import ru.otus.basic.yampolskiy.protocol.Message;
import ru.otus.basic.yampolskiy.protocol.dto.UserLoginDTO;
import ru.otus.basic.yampolskiy.protocol.dto.UserRegistrationDTO;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;


public class ChatServer {
    private final static int PORT = 9090;
    private static final Logger logger = LogManager.getLogger(ChatServer.class);
    private static final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);
    private static final BlockingQueue<Client<?>> connections = new LinkedBlockingQueue<>();
    private static final BlockingQueue<Client<UserRegistrationDTO>> registration = new LinkedBlockingQueue<>();
    private static final BlockingQueue<Client<UserLoginDTO>> authentication = new LinkedBlockingQueue<>();
    private static final BlockingQueue<Client<Message>> messages = new LinkedBlockingQueue<>();

    public void start() {
        try (ServerSocket chatServer = new ServerSocket(PORT)) {
            logger.info("Сервер запущен на порту: {}", PORT);

            executorService.scheduleWithFixedDelay(new ConnectionHandler(connections, registration, authentication), 0, 1000, TimeUnit.MILLISECONDS);
            executorService.scheduleWithFixedDelay(new RegistrationHandler(registration, authentication), 0, 1000, TimeUnit.MILLISECONDS);
            executorService.scheduleWithFixedDelay(new AuthenticationHandler(authentication, messages), 0, 1000, TimeUnit.MILLISECONDS);
            executorService.scheduleWithFixedDelay(new MessageHandler(messages), 0, 1000, TimeUnit.MILLISECONDS);

            while (true) {
                Socket socket = chatServer.accept();
                logger.info("Запрос подключения с адреса {}:{}", socket.getInetAddress(), socket.getPort());
                connections.put(new Client<>(this, socket));
            }
        } catch (Exception e) {
            logger.error("Критическая ошибка", e);
        } finally {
            shutdownExecutorService();
        }
    }

    private static void shutdownExecutorService() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
                if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                    logger.error("ExecutorService не завершился");
                }
            }
        } catch (InterruptedException ex) {
            logger.error("Ожидание завершения ExecutorService было прервано", ex);
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
        logger.info("Сервер завершил работу");
    }

    public void closeResources(InputStream in, OutputStream out, Socket socket) {
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                logger.error("Ошибка при закрытии DataInputStream", e);
            }
        }
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                logger.error("Ошибка при закрытии DataOutputStream", e);
            }
        }
        if (socket != null && !socket.isClosed()) {
            try {
                socket.close();
                logger.info("Сокет клиента закрыт: {}", socket.getRemoteSocketAddress());
            } catch (IOException e) {
                logger.error("Ошибка при закрытии сокета", e);
            }
        }
    }
}
