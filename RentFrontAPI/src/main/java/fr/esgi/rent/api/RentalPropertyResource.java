package fr.esgi.rent.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Path("/rental-properties")
public class RentalPropertyResource {


    @GET
    public String getRentalProperties() {
        return "coucou";
        /*
        String responseBody = "pas de reponse";
        try {
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofMinutes(1))
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:3000/hero"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            int statusCode = response.statusCode();
            responseBody = response.body();
            System.out.println(responseBody);
            if (statusCode >= 200 && statusCode < 300) {
                return responseBody;
            } else if (statusCode >= 400 && statusCode < 500) {
                return ("Client error: " + responseBody);
            } else if (statusCode >= 500 && statusCode < 600) {
                return ("Server error: " + responseBody);
            } else {
                return ("Unknown status code: " + statusCode);
            }
        }
        catch (IOException e){
            return "IOException : " + e;
        }
        catch (InterruptedException e){
            return "InterruptedException : " + e;
        }*/

    }

    /*@GET
    @Path("/{playerId}")
    public String getRentalProperty(@PathParam("playerId") String playerId) {
        String responseBody = "pas de reponse";
        try {
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofMinutes(1))
                    .build();

            String url = "http://localhost:3000/user/" + playerId;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            int statusCode = response.statusCode();
            responseBody = response.body();
            System.out.println(responseBody);
            if (statusCode >= 200 && statusCode < 300) {
                return responseBody;
            } else if (statusCode >= 400 && statusCode < 500) {
                return ("Client error: " + responseBody);
            } else if (statusCode >= 500 && statusCode < 600) {
                return ("Server error: " + responseBody);
            } else {
                return ("Unknown status code: " + statusCode);
            }
        }
        catch (IOException e){
            return "IOException : " + e;
        }
        catch (InterruptedException e){
            return "InterruptedException : " + e;
        }

    }*/
}