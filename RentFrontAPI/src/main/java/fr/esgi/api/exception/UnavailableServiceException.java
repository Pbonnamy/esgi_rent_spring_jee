package fr.esgi.api.exception;

public class UnavailableServiceException extends RuntimeException {

    public UnavailableServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnavailableServiceException(String message) {
        super(message);
    }

}