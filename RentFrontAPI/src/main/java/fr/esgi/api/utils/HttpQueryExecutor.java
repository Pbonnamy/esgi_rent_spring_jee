package fr.esgi.api.utils;

import jakarta.ws.rs.core.Response;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpQueryExecutor {

    HttpClient httpClient;

    public HttpQueryExecutor(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Response executeQuery(HttpRequest request) {
        try {
            HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            return Response.status(httpResponse.statusCode())
                    .entity(httpResponse.body())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}