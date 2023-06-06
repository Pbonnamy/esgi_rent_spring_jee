package fr.rent.application.error;

import fr.rent.exception.RentPropertyNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RentPropertyNotFoundException.class)
    public ErrorDto handleNotFoundRentalPropertyException(RentPropertyNotFoundException notFoundRentalPropertyException) {
        return new ErrorDto(notFoundRentalPropertyException.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorDto MethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        return new ErrorDto(methodArgumentNotValidException.getMessage());
    }


}
