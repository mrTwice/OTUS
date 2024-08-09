package ru.otus.java.basic.http.server.processors;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.List;

public class GetAllItemsProcessor implements RequestProcessor {
    private ObjectMapper objectMapper = ObjectMapperSingleton.getInstance();
    private ItemsRepository itemsRepository;

    public GetAllItemsProcessor(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }

    @Override
    public void execute(HttpRequest request, OutputStream out) throws IOException {
        List<Item> items = itemsRepository.getItems();
        String itemsJson = objectMapper.writeValueAsString(items);
        String response = new HttpResponse.Builder()
                .setProtocolVersion(request.getProtocolVersion())
                .setStatus(HttpStatus.OK)
                .addHeader(HttpHeader.CONTENT_TYPE, "application/json")
                .setBody(itemsJson)
                .build()
                .toString();
        out.write(response.getBytes(StandardCharsets.UTF_8));
    }
}
