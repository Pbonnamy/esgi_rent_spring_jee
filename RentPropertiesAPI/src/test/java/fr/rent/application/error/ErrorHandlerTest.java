package fr.rent.application.error;

import fr.rent.exception.RentPropertyNotFoundException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class ErrorHandlerTest {

    @Test
    void shouldHandleNotFoundRentalPropertyException() {
        String message = "Message";

        ErrorHandler errorHandler = new ErrorHandler();

        ErrorDto errorDto = errorHandler.handleNotFoundRentalPropertyException(new RentPropertyNotFoundException(message));

        assertThat(errorDto.message()).isEqualTo(message);
    }

    @Test
    void shouldHandleMethodArgumentNotValidException() {
        String message = "One of the field is missing or is incorrect";

        ErrorHandler errorHandler = new ErrorHandler();

        ErrorDto errorDto = errorHandler.handleMethodArgumentNotValid();

        assertThat(errorDto.message()).isEqualTo(message);
    }

    @Test
    void shouldHandleHttpMessageNotReadableException() {
        String message = "Request is invalid or one of the fields is missing";

        ErrorHandler errorHandler = new ErrorHandler();

        ErrorDto errorDto = errorHandler.handleHttpMessageNotReadableException();

        assertThat(errorDto.message()).isEqualTo(message);
    }


}
