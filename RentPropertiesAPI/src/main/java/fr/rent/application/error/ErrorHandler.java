package fr.rent.application.error;

import fr.rent.exception.RentPropertyBadRequest;
import fr.rent.exception.RentPropertyNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.Objects;

@RestControllerAdvice
public class ErrorHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RentPropertyNotFoundException.class)
    public ErrorDto handleNotFoundRentalPropertyException(RentPropertyNotFoundException notFoundRentalPropertyException) {
        return new ErrorDto(notFoundRentalPropertyException.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorDto handleMethodArgumentNotValid(MethodArgumentNotValidException methodArgumentNotValidException) {
        String message = Arrays.stream(Objects.requireNonNull(methodArgumentNotValidException.getDetailMessageArguments())).toList().get(methodArgumentNotValidException.getDetailMessageArguments().length-1).toString();
        return new ErrorDto(new RentPropertyBadRequest(message).getMessage());
    }


}
