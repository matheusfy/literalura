package io.github.matheusfy.litarelura.connections;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;

import io.github.matheusfy.litarelura.connections.exception.ConnectionTimeoutException;

public class BooksApi {

    private final HttpClient client = HttpClient.newHttpClient();
    private final String API_URL;
    private final String SEARCH_URL;

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

    private HttpResponse<String> sendRequest(HttpRequest request) throws IOException, InterruptedException {
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public String getSearch(String busca) throws IOException, InterruptedException {
        String url = API_URL + SEARCH_URL + busca;
        System.out.println(url);

        try {
            HttpRequest request = buildRequest(url);
            HttpResponse<String> response = sendRequest(request);
            return response.body();
        } catch (HttpTimeoutException e) {
            throw new ConnectionTimeoutException("Tempo de conexão excedido.");
        }
    }

}
