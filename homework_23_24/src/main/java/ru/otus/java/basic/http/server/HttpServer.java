package ru.otus.java.basic.http.server;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private Logger logger = LogManager.getLogger(HttpServer.class);
    private int port;
    private Dispatcher dispatcher;
    private ExecutorService threadPool;

    public HttpServer(int port) {
        this.port = port;
        this.dispatcher = new Dispatcher();
        this.threadPool = Executors.newCachedThreadPool();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.log(Level.INFO, "Сервер запущен на порту: {}", port);
            while (true) {
                Socket socket = serverSocket.accept();
                threadPool.submit(new RequestHandler(socket, dispatcher));
            }
        } catch (IOException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
    }
}
