package fr.rent.exception;

public class RentPropertyNotFoundException extends RuntimeException{

    public RentPropertyNotFoundException() {
        super("Impossible de trouver la propriété");
    }

    public RentPropertyNotFoundException(int id) {
        super("Impossible de trouver la propriété avec l'id " + id);
    }

}
