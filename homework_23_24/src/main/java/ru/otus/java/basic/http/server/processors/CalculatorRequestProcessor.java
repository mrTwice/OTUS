package ru.otus.java.basic.http.server.processors;

import ru.otus.java.basic.http.server.BadRequestException;
import ru.otus.java.basic.http.server.http.HttpHeader;
import ru.otus.java.basic.http.server.http.HttpRequest;
import ru.otus.java.basic.http.server.http.HttpResponse;
import ru.otus.java.basic.http.server.http.HttpStatus;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class CalculatorRequestProcessor implements RequestProcessor {
    @Override
    public void execute(HttpRequest request, OutputStream out) throws IOException {
        if (!request.containsParameter("a")) {
            throw new BadRequestException("Parameter 'a' is missing");
        }
        if (!request.containsParameter("b")) {
            throw new BadRequestException("Parameter 'b' is missing");
        }
        int a, b;
        try {
            a = Integer.parseInt(request.getParameter("a"));
        } catch (NumberFormatException e) {
            throw new BadRequestException("Parameter 'a' has incorrect type");
        }
        try {
            b = Integer.parseInt(request.getParameter("b"));
        } catch (NumberFormatException e) {
            throw new BadRequestException("Parameter 'b' has incorrect type");
        }

        String result = a + " + " + b + " = " + (a + b);
        String response = new HttpResponse.Builder()
                .setProtocolVersion(request.getProtocolVersion())
                .setStatus(HttpStatus.OK)
                .addHeader(HttpHeader.CONTENT_TYPE, "text/html")
                .setBody("<html><body><h1>" + result + "</h1></body></html>")
                .build()
                .toString();
        out.write(response.getBytes(StandardCharsets.UTF_8));
    }
}
