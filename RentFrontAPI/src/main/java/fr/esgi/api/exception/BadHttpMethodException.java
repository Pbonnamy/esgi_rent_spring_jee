package fr.esgi.api.exception;

public class BadHttpMethodException extends RuntimeException {

    public BadHttpMethodException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadHttpMethodException(String message) {
        super(message);
    }

}