package fr.esgi.api.api.utils;

import fr.esgi.api.constants.Constants;
import fr.esgi.api.exception.MalformedUriException;
import fr.esgi.api.utils.UrlCreationUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UrlCreationUtilsTest {

    @Test
    void urlPreparator_withPropertiesUriTarget_shouldReturnCorrectUrl() {
        // Arrange
        String requestUri = Constants.BASE_FRONT_URI + Constants.RENTAL_PROPERTIES_URI;
        String expectedUrl = Constants.BASE_PROPERTIES_API_URI + Constants.PROPERTIES_URI_TARGET + "/" + Constants.RENTAL_PROPERTIES_URI;

        // Act
        String actualUrl = UrlCreationUtils.urlPreparator(requestUri, Constants.PROPERTIES_URI_TARGET);

        // Assert
        Assertions.assertEquals(expectedUrl, actualUrl);
    }

    @Test
    void urlPreparator_withCarsUriTarget_shouldReturnCorrectUrl() {
        // Arrange
        String requestUri = Constants.BASE_FRONT_URI + Constants.RENTAL_CARS_URI;
        String expectedUrl = Constants.BASE_CARS_API_URI + Constants.CARS_URI_TARGET + "/" + Constants.RENTAL_CARS_URI;


        // Act
        String actualUrl = UrlCreationUtils.urlPreparator(requestUri, Constants.CARS_URI_TARGET);

        // Assert
        Assertions.assertEquals(expectedUrl, actualUrl);
    }

    @Test
    void urlPreparator_withUnknownUriTarget_shouldThrowException() {
        // Arrange
        String requestUri = Constants.BASE_FRONT_URI + "unknown/";

        // Act & Assert
        Assertions.assertThrows(MalformedUriException.class, () -> {
            UrlCreationUtils.urlPreparator(requestUri, "unknown");
        });
    }

    @Test
    void getBackTarget_withRentalCarsUri_shouldReturnCarsUriTarget() throws MalformedUriException {
        // Arrange
        String requestUrl = Constants.BASE_FRONT_URI + Constants.RENTAL_CARS_URI + "/Unknown";

        // Act
        String actualTarget = UrlCreationUtils.getBackTarget(requestUrl);

        // Assert
        Assertions.assertEquals(Constants.CARS_URI_TARGET, actualTarget);
    }

    @Test
    void getBackTarget_withRentalPropertiesUri_shouldReturnPropertiesUriTarget() throws MalformedUriException {
        // Arrange
        String requestUrl = Constants.BASE_FRONT_URI + Constants.RENTAL_PROPERTIES_URI;


        // Act
        String actualTarget = UrlCreationUtils.getBackTarget(requestUrl);

        // Assert
        Assertions.assertEquals(Constants.PROPERTIES_URI_TARGET, actualTarget);
    }

    @Test
    void getBackTarget_withUnknownUri_shouldThrowException() {
        // Arrange
        String url = Constants.BASE_PROPERTIES_API_URI + "unknown/";

        // Act & Assert
        Assertions.assertThrows(MalformedUriException.class, () -> {
            UrlCreationUtils.getBackTarget(url);
        });
    }

    @Test
    void getBackTarget_withInvalidUri_shouldThrowException() {
        // Arrange
        String url = Constants.FRONT_API_URI + "/unknown/";

        // Act & Assert
        Assertions.assertThrows(MalformedUriException.class, () -> {
            UrlCreationUtils.getBackTarget(url);
        });
    }

    @Test
    void getBackTarget_withEmptyUri_shouldThrowException() {
        // Arrange
        String url = Constants.BASE_FRONT_URI;

        // Act & Assert
        Assertions.assertThrows(MalformedUriException.class, () -> {
            UrlCreationUtils.getBackTarget(url);
        });
    }
}
