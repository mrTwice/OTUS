package ru.otus.basic.yampolskiy;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    private int port;
    private List<ClientHandler> clients;
    private AuthenticationProvider authenticationProvider;

    public AuthenticationProvider getAuthenticationProvider() {
        return authenticationProvider;
    }

    public Server(int port) {
        this.port = port;
        this.clients = new ArrayList<>();
        this.authenticationProvider = new InMemoryAuthenticationProvider(this);
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен на порту: " + port);
            authenticationProvider.initialize();
            while (true) {
                Socket socket = serverSocket.accept();
                new ClientHandler(this, socket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void subscribe(ClientHandler clientHandler) {
        broadcastMessage("В чат зашел: " + clientHandler.getUsername());
        clients.add(clientHandler);
    }

    public synchronized void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        broadcastMessage("Из чата вышел: " + clientHandler.getUsername());
    }

    public synchronized void kickUser(String username) {
        ClientHandler clientHandler = null;
        for (ClientHandler client : clients) {
            if(client.getUsername().equals(username)){
                clientHandler = client;
                break;
            }
        }
        if (clientHandler != null) {
            clients.remove(clientHandler);
            clientHandler.sendMessage("Вы удалены из чата");
            clientHandler.sendMessage("/kicked");
            broadcastMessage("Пользователь " + clientHandler.getUsername() + " удален администратором");
        }
    }

    public synchronized void broadcastMessage(String message) {
        for (ClientHandler c : clients) {
            c.sendMessage(message);
        }
    }

    public synchronized void sendPrivateMessage(String sender, String accepter, String message) {
        for (ClientHandler client : clients) {
            if(client.getUsername().equals(accepter)){
                client.sendMessage(ANSI_RED + "Приватное сообщение от "+ sender +": "+ ANSI_RESET +message);
            }
        }
    }

    public boolean isUsernameBusy(String username) {
        for (ClientHandler c : clients) {
            if (c.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
}
