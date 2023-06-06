package fr.rent.exception;

public class RentPropertyBadRequest extends RuntimeException{

    public RentPropertyBadRequest() {
        super("La requête est incorrecte");
    }

    public RentPropertyBadRequest(String message) {
        super("La requête est incorrecte pour la raison suivante: " + message);
    }

}
