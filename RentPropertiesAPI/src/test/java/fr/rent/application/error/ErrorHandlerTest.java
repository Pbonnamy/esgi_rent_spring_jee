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

}
