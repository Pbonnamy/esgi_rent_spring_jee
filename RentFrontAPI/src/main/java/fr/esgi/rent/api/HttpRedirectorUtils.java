package fr.esgi.rent.api;

import fr.esgi.rent.exception.MalformedUriException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Objects;

public class HttpRedirectorUtils {

    public static final String FRONT_API_URI = "front-api";
    public static final String RENTAL_PROPERTIES_FRONT_URI = "rental-properties";
    public static final String RENTAL_CARS_FRONT_URI = "rental_cars";
    public static final String PROPERTIES_URI_TARGET = "rent-properties-api";
    public static final String CARS_URI_TARGET = "rent-cars-api";
    public static final int SPRING_PORT = 3000;
    public static final String BASE_URL = "http:localhost:" + SPRING_PORT + FRONT_API_URI;


    Response httpQueryRedirection(UriInfo uriInfo, String method, String target) {
        try {
            if (Objects.equals(target, RENTAL_PROPERTIES_FRONT_URI)) {
                //SEND REQUEST TO PROPERTIES BACK
                String uriCreation = String.valueOf(uriInfo.getRequestUri());
                uriCreation = uriCreation.replace(FRONT_API_URI, PROPERTIES_URI_TARGET);
                URI uri = UriBuilder.fromUri(uriCreation)
                        .port(SPRING_PORT)
                        .build();
                return this.queryExecutor(uri, method);
            } else if (Objects.equals(target, RENTAL_CARS_FRONT_URI)) {
                //SEND REQUEST TO CARS BACK
                String uriCreation = String.valueOf(uriInfo.getRequestUri());
                uriCreation = uriCreation.replace(FRONT_API_URI, CARS_URI_TARGET);
                URI uri = UriBuilder.fromUri(uriCreation)
                        .port(SPRING_PORT)
                        .build();
                return this.queryExecutor(uri, method);
            } else {
                throw new MalformedUriException("Error 404 : url not found");
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Cannot access back services", e);
        }

    }

    Response queryExecutor(URI uri, String method) {
        try {
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofMinutes(1))
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();
            HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

            return Response.status(Response.Status.OK)
                    .entity(httpResponse.body())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Response transferRequest(UriInfo uriInfo, String method) throws MalformedUriException {
        String url = uriInfo.getRequestUri().toString();

        try {
            String target = this.getBackTarget(url);
            return this.httpQueryRedirection(uriInfo, method, target);
        } catch (MalformedUriException e) {
            throw new MalformedUriException("Error while trying to parse URL", e);
        } catch (RuntimeException e) {
            throw new RuntimeException("Cannot access back service", e);
        }
    }

    String getBackTarget(String url) throws MalformedUriException {
        try {
            String[] uriParts = url.split(FRONT_API_URI);
            if (uriParts.length < 2) {
                throw new MalformedUriException("No URL provided: " + url);
            }

            String uri = uriParts[1];
            if ("/".equals(uri) || null == uri) {
                throw new MalformedUriException("No URL provided: " + uri);
            }
            if(uri.equals(RENTAL_CARS_FRONT_URI)){
                return CARS_URI_TARGET;
            }
            if(uri.equals(RENTAL_PROPERTIES_FRONT_URI)){
                return PROPERTIES_URI_TARGET;
            }

            String[] uriSplit = uri.split("/");
            if (uriSplit.length < 2) {
                throw new MalformedUriException("Invalid URL: " + uri);
            }

            return uriSplit[1];
        } catch (MalformedUriException e) {
            throw new MalformedUriException("Error in URL", e);
        }
    }
}
