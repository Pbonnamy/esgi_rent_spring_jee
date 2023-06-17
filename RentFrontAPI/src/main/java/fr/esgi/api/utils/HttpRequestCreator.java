package fr.esgi.api.utils;

import fr.esgi.api.constants.HttpMethod;
import fr.esgi.api.exception.BadHttpMethodException;

import java.net.URI;
import java.net.http.HttpRequest;

public class HttpRequestCreator {

    public <T> HttpRequest create(URI uri, HttpMethod httpMethod, Object body) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(uri);

        switch (httpMethod) {
            case GET -> builder.GET();
            case POST -> builder.POST((HttpRequest.BodyPublisher) body);
            case PUT, PATCH -> builder.PUT((HttpRequest.BodyPublisher) body);
            case DELETE -> builder.DELETE();
            default -> throw new BadHttpMethodException("Unknown HTTP method: " + httpMethod);
        }
        return builder.build();
    }
}
