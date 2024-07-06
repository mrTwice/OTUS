package ru.otus.basic.yampolskiy;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Client {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private Scanner scanner;
    private AtomicBoolean flag;

    public Client() throws IOException {
        scanner = new Scanner(System.in);
        socket = new Socket("localhost", 8189);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        flag = new AtomicBoolean(false);


        Thread serverReaderThread = new Thread(() -> {
            try {
                while (true) {
                    String message = in.readUTF();
                    if (message.equals("/exitok")) {
                        flag.set(true);
                        break;
                    }
                    if (message.startsWith("/authok ")) {
                        System.out.println("Удалось успешно войти в чат под именем пользователя: " + message.split(" ")[1]);
                        continue;
                    }
                    if (message.startsWith("/regok ")) {
                        System.out.println("Удалось успешно пройти регистрацию и войти в чат под именем пользователя: " + message.split(" ")[1]);
                        continue;
                    }
                    if (message.equals("/kicked")) {
                        flag.set(true);
                        break;
                    }
                    System.out.println(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                disconnect();
            }
        });
        serverReaderThread.start();


        Thread consoleReaderThread = new Thread(() -> {
            try {
                while (!flag.get()) {
                    if (System.in.available() > 0) {
                        byte[] buffer = new byte[1024];
                        int bytesRead = System.in.read(buffer);
                        String message = new String(buffer, 0, bytesRead).trim();
                        if (!flag.get()) {
                            out.writeUTF(message);
                            out.flush();
                        }
                        if (message.equals("/exit")) {
                            flag.set(true);
                            break;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                disconnect();
            }
        });
        consoleReaderThread.start();

        if (flag.get()) {
            System.out.println("Завершение работы");
        }
    }

    private void disconnect() {
        try {
            if (scanner != null) {
                scanner.close();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
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

    public static void main(String[] args) {
        try {
            new Client();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

