package fr.esgi.api.exception;

public class MalformedUriException extends RuntimeException {

    public MalformedUriException(String message, Throwable cause) {
        super(message, cause);
    }

    public MalformedUriException(String message) {
        super(message);
    }

}