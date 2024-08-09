package ru.otus.java.basic.http.server;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.java.basic.http.server.http.HttpRequest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import static ru.otus.java.basic.http.server.http.HttpParser.parseRawHttp;

public class RequestHandler implements Runnable {
    private Logger logger = LogManager.getLogger(RequestHandler.class);
    private Socket socket;
    private Dispatcher dispatcher;

    public RequestHandler(Socket socket, Dispatcher dispatcher) throws IOException {
        this.socket = socket;
        this.dispatcher = dispatcher;
    }

    @Override
    public void run() {
        try (InputStream in = socket.getInputStream();
             OutputStream out = socket.getOutputStream()) {
            byte[] buffer = new byte[8192];
            int n = in.read(buffer);
            String rawRequest = new String(buffer, 0, n);
            logger.log(Level.DEBUG, rawRequest);
            HttpRequest httpRequest = parseRawHttp(rawRequest);
            dispatcher.execute(httpRequest, out);
        } catch (IOException e) {
            logger.log(Level.WARN, e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                logger.log(Level.WARN, e.getMessage());
            }
        }
    }
}
