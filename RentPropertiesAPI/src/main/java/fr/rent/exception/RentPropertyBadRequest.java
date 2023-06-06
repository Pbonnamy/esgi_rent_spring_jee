package fr.rent.exception;

public class RentPropertyBadRequest extends RuntimeException{

    public RentPropertyBadRequest(String message) {
        super("La requête est incorrecte pour la raison suivante: " + message);
    }

    public RentPropertyBadRequest() {
        super("La requête est incorrecte");
    }


}
