package io.github.matheusfy.litarelura.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import io.github.matheusfy.litarelura.model.entity.Book;
import io.github.matheusfy.litarelura.model.entity.dto.BookDTO;

import java.io.IOException;
import java.util.List;

public class JsonConverter {
    private final ObjectMapper mapper = new ObjectMapper();

    public List<BookDTO> json2Books(String json){
        try{
            json = mapper.readTree(json).get("results").toString();
            return convertToBooks(json, mapper.getTypeFactory().constructCollectionType(List.class, BookDTO.class));
        } catch (Exception error){
            throw new RuntimeException();
        }
    }

    private List<BookDTO> convertToBooks(String  json, CollectionType listType) {
        try {
            return mapper.readValue(json, listType);
        } catch (IOException e) {
            System.out.println("erro no mapeamento: " + e.getMessage());
        }
        return null;
    }
}
