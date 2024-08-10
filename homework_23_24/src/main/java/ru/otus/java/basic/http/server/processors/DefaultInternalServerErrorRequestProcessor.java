package ru.otus.java.basic.http.server.processors;


import ru.otus.java.basic.http.server.http.HttpHeader;
import ru.otus.java.basic.http.server.http.HttpRequest;
import ru.otus.java.basic.http.server.http.HttpResponse;
import ru.otus.java.basic.http.server.http.HttpStatus;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class DefaultInternalServerErrorRequestProcessor implements RequestProcessor {
    @Override
    public void execute(HttpRequest request, OutputStream out) throws IOException {
        String response = new HttpResponse.Builder()
                .setProtocolVersion(request.getProtocolVersion())
                .setStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .addHeader(HttpHeader.CONTENT_TYPE, "text/html")
                .setBody("<html><body><h1>INTERNAL SERVER ERROR</h1></body></html>")
                .build()
                .toString();
        out.write(response.getBytes(StandardCharsets.UTF_8));
    }
}
