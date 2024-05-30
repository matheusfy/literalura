package io.github.matheusfy.litarelura.connections;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class BooksApi {

    private final HttpClient client = HttpClient.newHttpClient();
    private final String API_URL;
    private final String SEARCH_URL;
    private final ObjectMapper mapper = new ObjectMapper();

    public BooksApi() {
        this.API_URL = System.getenv("API_URL");
        this.SEARCH_URL = "/?search=";
    }

    private HttpRequest buildRequest(String url) {
        try {
            return HttpRequest.newBuilder(new URI(url)).timeout(Duration.ofSeconds(5)).GET().build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpResponse<String> sendRequest(HttpRequest request) {
        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String getSearch(String busca) {
        String url = API_URL + SEARCH_URL + busca;
        System.out.println(url);
        HttpRequest request = buildRequest(url);
        HttpResponse<String> response = sendRequest(request);

        return response.body();

    }
}
