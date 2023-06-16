package fr.rent.exception;

public class RentPropertyNotFoundException extends RuntimeException{


    public RentPropertyNotFoundException(int id) {
        super("Impossible to find property with id " + id);
    }

    public RentPropertyNotFoundException(String message) {
        super(message);
    }

}
