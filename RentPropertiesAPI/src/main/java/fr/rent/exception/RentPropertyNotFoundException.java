package fr.rent.exception;

public class RentPropertyNotFoundException extends RuntimeException{


    public RentPropertyNotFoundException(int id) {
        super("Impossible de trouver la propriété avec l'id " + id);
    }

    public RentPropertyNotFoundException(String message) {
        super(message);
    }

}
