package io.github.matheusfy.litarelura.connections;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import io.github.matheusfy.litarelura.model.entity.Book;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.Paths.*;

public class BooksApi {

    private final HttpClient client = HttpClient.newHttpClient();
    private final String API_URL;
    private final String SEARCH_URL;
    private final ObjectMapper mapper = new ObjectMapper();

    public BooksApi(){
        this.API_URL = System.getenv("API_URL");
        this.SEARCH_URL = "/?search=";
    }

    private HttpRequest buildRequest(String url){
        try {
            return HttpRequest.newBuilder(new URI(url)).GET().build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpResponse<String> sendRequest(HttpRequest request){
        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String getSearch(String busca){
        String url = API_URL + SEARCH_URL + busca;
        System.out.println(url);
        HttpRequest request = buildRequest(url);
        HttpResponse<String> response = sendRequest(request);

        return response.body();
    }

    // TODO: Apagar -- funcao apenas de test da conversão do json para objetos de livros
    public void jsonToBook(){
        try {

            String fileContent = "";

            Path path = get("book.json");
            fileContent = new String(Files.readAllBytes(path));
            JsonNode nodeResults = mapper.readTree(fileContent).get("results");

            String json = nodeResults.toString();
            CollectionType listType = mapper.getTypeFactory().constructCollectionType(List.class, Book.class);
            List<Book> books;
            books = convertToBooks(json, listType);

        } catch (IOException e) {
            System.out.println("Falhou " + e.getMessage());
        }
    }

    private List<Book> convertToBooks(String json, CollectionType listType) {
        try {
            return mapper.readValue(json, listType);
        } catch (JsonProcessingException e) {
            System.out.println("erro no mapeamento: " + e.getMessage());
        }
        return null;
    }

}
