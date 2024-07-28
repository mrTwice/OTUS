package ru.otus.java.basic.http.server.processors;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.java.basic.http.server.BadRequestException;
import ru.otus.java.basic.http.server.ObjectMapperSingleton;
import ru.otus.java.basic.http.server.app.Item;
import ru.otus.java.basic.http.server.app.ItemsRepository;
import ru.otus.java.basic.http.server.http.HttpHeader;
import ru.otus.java.basic.http.server.http.HttpRequest;
import ru.otus.java.basic.http.server.http.HttpResponse;
import ru.otus.java.basic.http.server.http.HttpStatus;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class DeleteItemRequestProcessor implements RequestProcessor{
    private Logger logger = LogManager.getLogger(DeleteItemRequestProcessor.class);
    private ObjectMapper objectMapper = ObjectMapperSingleton.getInstance();
    private ItemsRepository itemsRepository;

    public DeleteItemRequestProcessor(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }

    @Override
    public void execute(HttpRequest request, OutputStream out) throws IOException {
        if (!request.containsParameter("id")) {
            throw new BadRequestException("Parameter 'id' is missing");
        }
        Long id;
        try {
            id = Long.parseLong(request.getParameter("id"));
        } catch (NumberFormatException e) {
            throw new BadRequestException("Parameter 'id' has incorrect type");
        }
        Item item = itemsRepository.deleteItem(id);
        String itemJson = objectMapper.writeValueAsString(item);
        String response = new HttpResponse.Builder()
                .setProtocolVersion(request.getProtocolVersion())
                .setStatus(HttpStatus.OK)
                .addHeader(HttpHeader.CONTENT_TYPE, "application/json")
                .setBody(itemJson)
                .build()
                .toString();
        out.write(response.getBytes(StandardCharsets.UTF_8));
    }
}
