package fr.esgi.rent.api;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Path("/rental-properties")
public class RentalPropertyResource {


    @GET
    public Object getRentalProperties() {
        try {
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofMinutes(1))
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:3000 /rent-properties-api/rental-properties"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            //Passer la response.body à un objet non typé permet de le parer
            Object o = response.body();
            return o;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
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