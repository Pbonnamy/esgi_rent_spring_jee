package fr.esgi.api.exception;

public class UnaviableServiceException extends RuntimeException {

    public UnaviableServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnaviableServiceException(String message) {
        super(message);
    }

}