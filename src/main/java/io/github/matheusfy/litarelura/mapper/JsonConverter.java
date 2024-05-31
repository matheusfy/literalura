package io.github.matheusfy.litarelura.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import io.github.matheusfy.litarelura.mapper.exception.JsonMappingFailException;
import io.github.matheusfy.litarelura.model.dto.BookDTO;

import java.io.IOException;
import java.util.List;

public class JsonConverter {
    private final ObjectMapper mapper = new ObjectMapper();

    public List<BookDTO> json2Books(String json) throws JsonMappingFailException {

        String field = "results";
        try {
            json = mapper.readTree(json).get(field).toString();
        } catch (JsonProcessingException e) {
            throw new JsonMappingFailException("Erro em mapear um dos campos do json: " + e.getMessage());
        }

        return convertToBooks(json, mapper.getTypeFactory().constructCollectionType(List.class, BookDTO.class));
    }

    private List<BookDTO> convertToBooks(String json, CollectionType listType) throws JsonMappingFailException {
        try {
            return mapper.readValue(json, listType);
        } catch (IOException e) {
            throw new JsonMappingFailException("Erro ao mapear json para lista de livros: " + e.getMessage(), json);
        }
    }
}
