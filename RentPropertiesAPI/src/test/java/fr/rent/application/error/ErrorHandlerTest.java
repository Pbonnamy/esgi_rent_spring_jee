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
        String message = "L'un des champs est manquant ou incorrect";

        ErrorHandler errorHandler = new ErrorHandler();

        ErrorDto errorDto = errorHandler.handleMethodArgumentNotValid();

        assertThat(errorDto.message()).isEqualTo(message);
    }

    @Test
    void shouldHandleHttpMessageNotReadableException() {
        String message = "La requête est mal formée ou un des champs est invalide";

        ErrorHandler errorHandler = new ErrorHandler();

        ErrorDto errorDto = errorHandler.handleHttpMessageNotReadableException();

        assertThat(errorDto.message()).isEqualTo(message);
    }


}
