package fr.esgi.api.utils;

import fr.esgi.api.HttpMethod;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpQueryExecutor {
    public Response executeQuery(HttpRequest request) {
        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

            return Response.status(Response.Status.OK)
                    .entity(httpResponse.body())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}