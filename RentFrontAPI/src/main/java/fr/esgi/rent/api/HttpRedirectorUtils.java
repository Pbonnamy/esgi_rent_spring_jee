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

    private static final String FRONT_API_URI = "front-api";
    private static final String PROPERTIES_URI = "rent-properties-api";
    private static final String CARS_URI = "rent-cars-api";
    private static final int SPRING_PORT = 3000;
    private static final String BASE_URL = "http:localhost:" + SPRING_PORT;


    private Response httpQueryRedirection(UriInfo uriInfo, String method, String target) {
        try {
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
                throw new MalformedUriException("Error 404 : url not found");
            }
        }
        catch (RuntimeException e){
            throw new RuntimeException("Cannot access back services", e);
        }

    }

    private Response queryExecutor(URI uri, String method) {
        try {
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofMinutes(1))
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:3000/hero"))
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
        }
        catch (RuntimeException e){
            throw new RuntimeException("Cannot access back service", e);
        }
    }

    private String getBackTarget(String url) throws MalformedUriException {
        try {
            String[] uriParts = url.split(FRONT_API_URI);
            if (uriParts.length < 2) {
                throw new MalformedUriException("No URL provided: " + url);
            }

            String uri = uriParts[1];
            if ("/".equals(uri) || null == uri) {
                throw new MalformedUriException("No URL provided: " + uri);
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
