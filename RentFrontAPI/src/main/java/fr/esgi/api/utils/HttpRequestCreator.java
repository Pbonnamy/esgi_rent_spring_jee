package fr.esgi.api.utils;

import fr.esgi.api.constants.HttpMethod;

import java.net.URI;
import java.net.http.HttpRequest;

public class HttpRequestCreator {

    public HttpRequest create(URI uri, HttpMethod httpMethod){
        return HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
    }
}
