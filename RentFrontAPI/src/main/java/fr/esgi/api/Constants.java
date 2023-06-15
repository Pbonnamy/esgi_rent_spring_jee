package fr.esgi.api;

public final class Constants {
    public static final String FRONT_API_URI = "front-api/";
    public static final String RENTAL_PROPERTIES_URI = "rental-properties";
    public static final String RENTAL_CARS_URI = "rental-cars";
    public static final String PROPERTIES_URI_TARGET = "rent-properties-api";
    public static final String CARS_URI_TARGET = "rent-cars-api";
    public static final int SPRING_PORT = 3000;
    public static final int FRONT_PORT = 8080;
    public static final String HOST = "http://localhost:";
    public static final String BASE_SPRING_URI = HOST + SPRING_PORT + "/";
    public static final String BASE_FRONT_URI = HOST + FRONT_PORT + "/" + FRONT_API_URI;

    private Constants() {
    }
}
