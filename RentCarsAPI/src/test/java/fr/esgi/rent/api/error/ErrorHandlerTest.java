package fr.esgi.rent.api.error;

import fr.esgi.rent.exception.NotFoundRentalCarException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ErrorHandlerTest {

    private final ErrorHandler errorHandler = new ErrorHandler();

    @Test
    void shouldHandleNotFoundRentalCarException() {
        String expectedMessage = "message";

        ErrorDto errorDto = errorHandler.handleNotFoundRentalCarException(new NotFoundRentalCarException(expectedMessage));

        assertEquals(expectedMessage, errorDto.message());
    }
}