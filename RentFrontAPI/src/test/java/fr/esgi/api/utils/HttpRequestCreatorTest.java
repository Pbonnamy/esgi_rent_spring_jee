package fr.esgi.api.utils;

import fr.esgi.api.constants.Constants;
import fr.esgi.api.constants.HttpMethod;
import fr.esgi.api.exception.BadHttpMethodException;
import fr.esgi.api.utils.HttpRequestCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.util.Objects;

@ExtendWith(MockitoExtension.class)
public class HttpRequestCreatorTest {

    @Test
    void create_shouldThrowBadHttpMethodException() {
        URI uri = URI.create(Constants.BASE_FRONT_URI + Constants.RENTAL_CARS_URI);
        HttpRequestCreator httpRequestCreator = new HttpRequestCreator();

        Assertions.assertThrows(BadHttpMethodException.class, () -> {
            HttpRequest request = httpRequestCreator.create(uri, HttpMethod.UNKNOWN, null);

        });
    }

    @Test
    void create_shouldReturnValidHttpRequest() {
        URI uri = URI.create(Constants.BASE_FRONT_URI + Constants.RENTAL_CARS_URI);
        HttpRequestCreator httpRequestCreator = new HttpRequestCreator();
        HttpRequest request = httpRequestCreator.create(uri, HttpMethod.GET, null);

        HttpHeaders header = request.headers();

        String token = header.firstValue("Authorization").orElse(null);

        Assertions.assertEquals(HttpMethod.GET.toString(), request.method());
        Assertions.assertEquals(uri, request.uri());
        Assertions.assertEquals("Bearer " + Constants.AUTH_TOKEN,  token);
    }

    @Test
    void create_shouldReturnValidHttpRequestPost() {
        URI uri = URI.create(Constants.BASE_FRONT_URI + Constants.RENTAL_CARS_URI);
        HttpRequestCreator httpRequestCreator = new HttpRequestCreator();
        HttpRequest request = httpRequestCreator.create(uri, HttpMethod.POST, "Body test");

        Assertions.assertEquals(HttpMethod.POST.toString(), request.method());
        Assertions.assertEquals(uri, request.uri());
    }

    @Test
    void create_shouldReturnValidHttpRequestPut() {
        URI uri = URI.create(Constants.BASE_FRONT_URI + Constants.RENTAL_CARS_URI);
        HttpRequestCreator httpRequestCreator = new HttpRequestCreator();
        HttpRequest request = httpRequestCreator.create(uri, HttpMethod.PUT, "Body test");

        Assertions.assertEquals(HttpMethod.PUT.toString(), request.method());
        Assertions.assertEquals(uri, request.uri());
    }

    @Test
    void create_shouldReturnValidHttpRequestPatch() {
        URI uri = URI.create(Constants.BASE_FRONT_URI + Constants.RENTAL_CARS_URI);
        HttpRequestCreator httpRequestCreator = new HttpRequestCreator();
        HttpRequest request = httpRequestCreator.create(uri, HttpMethod.PATCH, "Body test");

        Assertions.assertEquals(HttpMethod.PATCH.toString(), request.method());
        Assertions.assertEquals(uri, request.uri());
    }

    @Test
    void create_shouldReturnValidHttpRequestDelete() {
        URI uri = URI.create(Constants.BASE_FRONT_URI + Constants.RENTAL_CARS_URI);
        HttpRequestCreator httpRequestCreator = new HttpRequestCreator();
        HttpRequest request = httpRequestCreator.create(uri, HttpMethod.DELETE, "Body test");

        Assertions.assertEquals(HttpMethod.DELETE.toString(), request.method());
        Assertions.assertEquals(uri, request.uri());
    }


}
