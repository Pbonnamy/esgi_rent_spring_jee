package fr.esgi.api.utils;

import fr.esgi.api.exception.UnavailableServiceException;
import jakarta.ws.rs.core.MediaType;
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
                    .header("Content-Type", MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e) {
            throw new UnavailableServiceException(e.getMessage(), e);
        }
    }
}