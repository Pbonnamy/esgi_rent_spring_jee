package fr.esgi.api.api.utils;

import fr.esgi.api.constants.Constants;
import fr.esgi.api.constants.HttpMethod;
import fr.esgi.api.utils.HttpRequestCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;
import java.net.http.HttpRequest;

@ExtendWith(MockitoExtension.class)
public class HttpRequestCreatorTest {

    @Test
    void create_shouldReturnValidHttpRequest() {
        URI uri = URI.create(Constants.BASE_FRONT_URI + Constants.RENTAL_CARS_URI);
        HttpRequestCreator httpRequestCreator = new HttpRequestCreator();
        HttpRequest request = httpRequestCreator.create(uri, HttpMethod.GET);

        Assertions.assertEquals(HttpMethod.GET.toString(), request.method());
        Assertions.assertEquals(uri, request.uri());
    }


}
