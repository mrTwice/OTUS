package ru.otus.basic.yampolskiy.handlers;

import ru.otus.basic.yampolskiy.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ClientHandler(Server server, Socket socket) throws IOException {
        this.server = server;
        this.socket = socket;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());
        new Thread(() -> {
            try {
                System.out.println("Подключился новый клиент");
                while (true) {
                    String message = in.readUTF();
                    if (message.equals("/exit")) {
                        sendMessage("/exitok");
                        return;
                    }
                    if (message.startsWith("/auth ")) {
                        String[] elements = message.split(" ");
                        if (elements.length != 3) {
                            sendMessage("Неверный формат команды /auth");
                            continue;
                        }
                        if (server.getAuthenticationProvider().authenticate(this, elements[1], elements[2])) {
                            break;
                        }
                        continue;
                    }
                    if (message.startsWith("/register ")) {
                        String[] elements = message.split(" ");
                        if (elements.length != 4) {
                            sendMessage("Неверный формат команды /register");
                            continue;
                        }
                        if (server.getAuthenticationProvider().registration(this, elements[1], elements[2], elements[3])) {
                            break;
                        }
                        continue;
                    }
                    sendMessage("Перед работой с чатом необходимо выполнить аутентификацию '/auth login password' или регистрацию '/register login password username'");
                }
                while (true) {
                    if(in.available()>0){
                        String message = in.readUTF();
                        if (message.startsWith("/")) {
                            if (message.equals("/exit")) {
                                sendMessage("/exitok");
                                break;
                            }

                            if (message.startsWith("/private")) {
                                if (message.startsWith("/private ")) {
                                    String[] data = getDataPrivateMessage(message);
                                    server.sendPrivateMessage(username, data[0], data[1]);
                                } else {
                                    sendMessage("Неверная команда приватного сообщения.");
                                }
                            }

                            if (message.startsWith("/kick")) {
                                if (message.startsWith("/kick ")) {
                                    if (server.getAuthenticationProvider().permissionsGranted(this)) {
                                        String[] data = message.split(" ");
                                        server.kickUser(data[1]);
                                    }
                                } else {
                                    sendMessage("Недостаточно прав");
                                }
                                continue;
                            }
                            continue;
                        }

                        server.broadcastMessage(username + ": " + message);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                disconnect();
            }
        }).start();
    }

    public String[] getDataPrivateMessage(String rawMessage) {

        int startUsername = rawMessage.indexOf(" ") + 1;
        int endUsername = rawMessage.indexOf(" ", startUsername);
        int startMessage = endUsername + 1;
        String accepter = rawMessage.substring(startUsername, endUsername);
        String msg = rawMessage.substring(startMessage);
        return new String[]{accepter, msg};
    }

    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        server.unsubscribe(this);
        try {
            if (in != null) {
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (out != null) {
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
