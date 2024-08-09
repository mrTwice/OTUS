package ru.otus.java.basic.http.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.java.basic.http.server.app.ItemsRepository;
import ru.otus.java.basic.http.server.http.HttpHeader;
import ru.otus.java.basic.http.server.http.HttpRequest;
import ru.otus.java.basic.http.server.http.HttpResponse;
import ru.otus.java.basic.http.server.http.HttpStatus;
import ru.otus.java.basic.http.server.processors.*;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Dispatcher {
    private Logger logger = LogManager.getLogger(Dispatcher.class);
    private ObjectMapper objectMapper = ObjectMapperSingleton.getInstance();
    private Map<String, RequestProcessor> processors;
    private RequestProcessor defaultNotFoundRequestProcessor;
    private RequestProcessor defaultInternalServerErrorProcessor;

    private ItemsRepository itemsRepository;

    public Dispatcher() {
        this.itemsRepository = new ItemsRepository();

        this.processors = new HashMap<>();
        this.processors.put("GET /", new HelloWorldRequestProcessor());
        this.processors.put("GET /another", new AnotherHelloWorldRequestProcessor());
        this.processors.put("GET /calculator", new CalculatorRequestProcessor());
        this.processors.put("GET /items", new GetAllItemsProcessor(itemsRepository));
        this.processors.put("DELETE /items", new DeleteItemRequestProcessor(itemsRepository));
        this.processors.put("POST /items", new CreateNewItemProcessor(itemsRepository));

        this.defaultNotFoundRequestProcessor = new DefaultNotFoundRequestProcessor();
        this.defaultInternalServerErrorProcessor = new DefaultInternalServerErrorRequestProcessor();
    }

    public void execute(HttpRequest request, OutputStream out) throws IOException {
        try {
            if (!processors.containsKey(request.getRoutingKey())) {
                defaultNotFoundRequestProcessor.execute(request, out);
                return;
            }
            processors.get(request.getRoutingKey()).execute(request, out);
        } catch (BadRequestException e) {
            logger.log(Level.WARN, e.getMessage());
            DefaultErrorDto defaultErrorDto = new DefaultErrorDto("CLIENT_DEFAULT_ERROR", e.getMessage());
            String jsonError = objectMapper.writeValueAsString(defaultErrorDto);
            String response = new HttpResponse.Builder()
                    .setProtocolVersion(request.getProtocolVersion())
                    .setStatus(HttpStatus.BAD_REQUEST)
                    .addHeader(HttpHeader.CONTENT_TYPE, "application/json")
                    .setBody(jsonError)
                    .build()
                    .toString();
            out.write(response.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            logger.log(Level.WARN, e.getMessage());
            defaultInternalServerErrorProcessor.execute(request, out);
        }
    }
}
