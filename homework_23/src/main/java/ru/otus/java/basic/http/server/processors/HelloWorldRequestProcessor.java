package ru.otus.java.basic.http.server.processors;

import ru.otus.java.basic.http.server.http.HttpHeader;
import ru.otus.java.basic.http.server.http.HttpRequest;
import ru.otus.java.basic.http.server.http.HttpResponse;
import ru.otus.java.basic.http.server.http.HttpStatus;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class HelloWorldRequestProcessor implements RequestProcessor {
    @Override
    public void execute(HttpRequest request, OutputStream out) throws IOException {
        String response = new HttpResponse.Builder()
                .setProtocolVersion(request.getProtocolVersion())
                .setStatus(HttpStatus.OK)
                .addHeader(HttpHeader.CONTENT_TYPE, "text/html")
                .setBody("<html><body><h1>Hello World</h1><table><tr><td>1</td><td>2</td></tr></table></body></html>")
                .build()
                .toString();
        out.write(response.getBytes(StandardCharsets.UTF_8));
    }
}
