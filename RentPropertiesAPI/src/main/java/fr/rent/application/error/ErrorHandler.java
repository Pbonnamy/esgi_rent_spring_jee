package fr.rent.application.error;

import fr.rent.exception.RentPropertyBadRequest;
import fr.rent.exception.RentPropertyNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
    public ErrorDto handleMethodArgumentNotValid() {
        return new ErrorDto(new RentPropertyBadRequest("One of the field is missing or is incorrect").getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorDto handleHttpMessageNotReadableException() {
        return new ErrorDto("Request is invalid or one of the fields is missing");
    }


}
