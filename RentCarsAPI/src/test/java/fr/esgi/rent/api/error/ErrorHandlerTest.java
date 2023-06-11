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

    @Test
    void shouldHandleMethodArgumentNotValidException() {
        String expectedMessage = "Invalid request body";

        ErrorDto errorDto = errorHandler.handleMethodArgumentNotValidException();

        assertEquals(expectedMessage, errorDto.message());
    }
}