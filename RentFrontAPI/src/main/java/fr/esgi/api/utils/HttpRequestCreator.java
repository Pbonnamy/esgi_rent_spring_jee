package fr.esgi.api.utils;

import fr.esgi.api.constants.HttpMethod;
import fr.esgi.api.exception.BadHttpMethodException;

import java.net.URI;
import java.net.http.HttpRequest;

public class HttpRequestCreator {

    public HttpRequest create(URI uri, HttpMethod httpMethod, String body) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(uri);

        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(body != null ? body : "");

        switch (httpMethod) {
            case GET -> builder.GET();
            case POST -> builder.POST(bodyPublisher);
            case PUT, PATCH -> builder.method(httpMethod.name(), bodyPublisher);
            case DELETE -> builder.DELETE();
            default -> throw new BadHttpMethodException("Unknown HTTP method: " + httpMethod);
        }

        return builder.build();
    }
}
