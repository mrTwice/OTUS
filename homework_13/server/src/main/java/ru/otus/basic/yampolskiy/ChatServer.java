package ru.otus.basic.yampolskiy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.basic.yampolskiy.entities.Client;
import ru.otus.basic.yampolskiy.handlers.*;
import ru.otus.basic.yampolskiy.handlers.AuthenticationHandleTask;
import ru.otus.basic.yampolskiy.handlers.RegistrationHandleTask;
import ru.otus.basic.yampolskiy.protocol.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.*;


public class ChatServer {
    private final static int PORT = 9090;
    private static final Logger logger = LogManager.getLogger(ChatServer.class);
    private static final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);
    private static final BlockingQueue<Client> newClients = new LinkedBlockingQueue<>();
    private static final BlockingQueue<Client> registration = new LinkedBlockingQueue<>();
    private static final BlockingQueue<Client> authentication = new LinkedBlockingQueue<>();
    private static final BlockingQueue<Client> authorizedClients = new LinkedBlockingQueue<>();
    private static final BlockingQueue<Message> messages = new LinkedBlockingQueue<>();

    public void start() {
        try (ServerSocket chatServer = new ServerSocket(PORT)) {
            logger.info("Сервер запущен на порту: {}", PORT);

            executorService.scheduleWithFixedDelay(new ConnectionHandleTask(newClients, registration, authentication), 0, 1000, TimeUnit.MILLISECONDS);
            executorService.scheduleWithFixedDelay(new RegistrationHandleTask(newClients, registration), 0, 1000, TimeUnit.MILLISECONDS);
            executorService.scheduleWithFixedDelay(new AuthenticationHandleTask(this,newClients, authentication, authorizedClients), 0, 1000, TimeUnit.MILLISECONDS);
            executorService.scheduleWithFixedDelay(new MessageHandleTask(this, authorizedClients, messages), 0, 1000, TimeUnit.MILLISECONDS);

            while (true) {
                Socket newConnection = chatServer.accept();
                logger.info("Запрос подключения с адреса {}:{}", newConnection.getInetAddress(), newConnection.getPort());
                Client newClient = new Client(newConnection);
                newClients.put(newClient);
                logger.info("Клиент отправлен в очередь новых подключений");
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
        } catch (InterruptedException e) {
            logger.error("Ожидание завершения ExecutorService было прервано", e);
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
        logger.info("Сервер завершил работу");
    }

    public void sendMessage(String message) throws IOException {
        for (Client client: authorizedClients){
                client.getOut().writeUTF(message);
                client.getOut().flush();
                logger.info("Данные отправлены клиенту: {}", client.getUser().getNickname());
        }
    }

    public void sendPrivateMessage(String message, String recipientEmail) throws IOException {
        Optional<Client> recipient = authorizedClients.stream().filter(c -> c.getUser().getEmail().equals(recipientEmail)).findFirst();
        if(recipient.isPresent()) {
            Client client = recipient.get();
            client.getOut().writeUTF(message);
            client.getOut().flush();
        }
    }
}
