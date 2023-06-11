package fr.esgi.rent.api;

import fr.esgi.rent.exception.MalFormattedUriException;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Objects;

public class HttpRedirectorUtils {

    private static final String FRONT_API_URI = "front-api";
    private static final String PROPERTIES_URI = "rent-properties-api";
    private static final String CARS_URI = "rent-cars-api";

    private static final int RENT_FRONT_API_PORT = 8080;
    private static final int SPRING_PORT = 3000;

    private static final String BASE_URL = "http:localhost:" + SPRING_PORT;


    private String httpQueryRedirection(UriInfo uriInfo, String method, String target) {
        if (Objects.equals(target, PROPERTIES_URI)) {
            //SEND REQUEST TO PROPERTIES BACK
            String uriCreation = String.valueOf(uriInfo.getRequestUri());
            uriCreation = uriCreation.replace(FRONT_API_URI, PROPERTIES_URI);
            URI uri = UriBuilder.fromUri(uriCreation)
                    .port(SPRING_PORT)
                    .build();
            return this.queryExecutor(uri, method);
        } else if (Objects.equals(target, CARS_URI)) {
            //SEND REQUEST TO CARS BACK
            String uriCreation = String.valueOf(uriInfo.getRequestUri());
            uriCreation = uriCreation.replace(FRONT_API_URI, CARS_URI);
            URI uri = UriBuilder.fromUri(uriCreation)
                    .port(SPRING_PORT)
                    .build();
            return this.queryExecutor(uri, method);
        } else {
            throw new MalFormattedUriException("Error 404 : url not found");
        }
    }

    private String queryExecutor(URI uri, String method) {
        try {
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofMinutes(1))
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public String transferRequest(UriInfo uriInfo, String method) throws MalFormattedUriException {
        String url = uriInfo.getRequestUri().toString();

        try {
            String target = this.getBackTarget(url);
            return httpQueryRedirection(uriInfo, method, target);
        } catch (MalFormattedUriException e) {
            throw new MalFormattedUriException("Error while trying to parse URL", e);
        }
    }

    private String getBackTarget(String url) throws MalFormattedUriException {
        try {
            String[] uriParts = url.split(FRONT_API_URI);
            if (uriParts.length < 2) {
                throw new MalFormattedUriException("No URL provided: " + url);
            }
            String uri = uriParts[1];
            if ("/".equals(uri) || null == uri) {
                throw new MalFormattedUriException("No URL provided: " + uri);
            }
            String[] uriSplit = uri.split("/");
            if (uriSplit.length < 2) {
                throw new MalFormattedUriException("Invalid URL: " + uri);
            }
            return uriSplit[1];
        } catch (MalFormattedUriException e) {
            throw new MalFormattedUriException("Error occurred in getBackTarget", e);
        }
    }


}
