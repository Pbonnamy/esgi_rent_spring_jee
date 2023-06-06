package fr.esgi.rent.api.error;

import fr.esgi.rent.exception.NotFoundRentalCarException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundRentalCarException.class)
    public ErrorDto handleNotFoundRentalCarException(NotFoundRentalCarException exception) {
        return new ErrorDto(exception.getMessage());
    }
}
