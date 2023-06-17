package fr.esgi.api.exception;

public class BadHttpMethodException extends RuntimeException {
    public BadHttpMethodException(String message) {
        super(message);
    }

}