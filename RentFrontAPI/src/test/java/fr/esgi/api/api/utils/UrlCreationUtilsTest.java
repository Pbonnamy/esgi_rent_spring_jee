package fr.esgi.api.api.utils;

import fr.esgi.api.exception.MalformedUriException;
import fr.esgi.api.utils.UrlCreationUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static fr.esgi.api.service.HttpRedirectorHandler.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UrlCreationUtilsTest {

    @Test
    void urlPreparator_withPropertiesUriTarget_shouldReturnCorrectUrl() {
        // Arrange
        String requestUri = BASE_FRONT_URI + RENTAL_PROPERTIES_URI;
        String expectedUrl = BASE_SPRING_URI + PROPERTIES_URI_TARGET + "/" + RENTAL_PROPERTIES_URI;

        // Act
        String actualUrl = UrlCreationUtils.urlPreparator(requestUri, PROPERTIES_URI_TARGET);

        // Assert
        Assertions.assertEquals(expectedUrl, actualUrl);
    }

    @Test
    void urlPreparator_withCarsUriTarget_shouldReturnCorrectUrl() {
        // Arrange
        String requestUri = BASE_FRONT_URI + RENTAL_CARS_URI;
        String expectedUrl = BASE_SPRING_URI + CARS_URI_TARGET + "/" + RENTAL_CARS_URI;


        // Act
        String actualUrl = UrlCreationUtils.urlPreparator(requestUri, CARS_URI_TARGET);

        // Assert
        Assertions.assertEquals(expectedUrl, actualUrl);
    }

    @Test
    void urlPreparator_withUnknownUriTarget_shouldThrowException() {
        // Arrange
        String requestUri = BASE_FRONT_URI + "unknown/";

        // Act & Assert
        Assertions.assertThrows(MalformedUriException.class, () -> {
            UrlCreationUtils.urlPreparator(requestUri, "unknown");
        });
    }

    @Test
    void getBackTarget_withRentalCarsUri_shouldReturnCarsUriTarget() throws MalformedUriException {
        // Arrange
        String requestUrl = BASE_FRONT_URI + RENTAL_CARS_URI;

        // Act
        String actualTarget = UrlCreationUtils.getBackTarget(requestUrl);

        // Assert
        Assertions.assertEquals(CARS_URI_TARGET, actualTarget);
    }

    @Test
    void getBackTarget_withRentalPropertiesUri_shouldReturnPropertiesUriTarget() throws MalformedUriException {
        // Arrange
        String requestUrl = BASE_FRONT_URI + RENTAL_PROPERTIES_URI;


        // Act
        String actualTarget = UrlCreationUtils.getBackTarget(requestUrl);

        // Assert
        Assertions.assertEquals(PROPERTIES_URI_TARGET, actualTarget);
    }

    @Test
    void getBackTarget_withUnknownUri_shouldThrowException() {
        // Arrange
        String url = BASE_SPRING_URI + "unknown/";

        // Act & Assert
        Assertions.assertThrows(MalformedUriException.class, () -> {
            UrlCreationUtils.getBackTarget(url);
        });
    }

    @Test
    void getBackTarget_withEmptyUri_shouldThrowException() {
        // Arrange
        String url = BASE_FRONT_URI;

        // Act & Assert
        Assertions.assertThrows(MalformedUriException.class, () -> {
            UrlCreationUtils.getBackTarget(url);
        });
    }
}
