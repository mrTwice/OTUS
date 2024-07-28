package ru.otus.java.basic.http.server.processors;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

public class CreateNewItemProcessor implements RequestProcessor {
    private ObjectMapper objectMapper = ObjectMapperSingleton.getInstance();
    private ItemsRepository itemsRepository;

    public CreateNewItemProcessor(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }

    @Override
    public void execute(HttpRequest request, OutputStream out) throws IOException {
        try {
            Item item = itemsRepository.add(objectMapper.readValue(request.getBody(), Item.class));
            String itemJson = objectMapper.writeValueAsString(item);
            String response = new HttpResponse.Builder()
                    .setProtocolVersion(request.getProtocolVersion())
                    .setStatus(HttpStatus.CREATED)
                    .addHeader(HttpHeader.CONTENT_TYPE, "application/json")
                    .setBody(itemJson)
                    .build()
                    .toString();
            out.write(response.getBytes(StandardCharsets.UTF_8));
        } catch (JsonParseException e) {
            e.printStackTrace();
            throw new BadRequestException("Некорректный формат входящего JSON объекта");
        }
    }
}
