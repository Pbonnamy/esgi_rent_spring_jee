package fr.esgi.rent.exception;

public class MalFormattedUriException extends RuntimeException {

    public MalFormattedUriException(String message, Throwable cause) {
        super(message, cause);
    }

    public MalFormattedUriException(String message) {
        super(message);
    }

}