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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}