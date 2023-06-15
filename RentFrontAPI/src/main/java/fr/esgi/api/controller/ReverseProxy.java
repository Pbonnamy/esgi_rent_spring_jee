package fr.esgi.api.controller;


import fr.esgi.api.HttpMethod;
import fr.esgi.api.exception.MalformedUriException;
import fr.esgi.api.service.HttpRedirectorHandler;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.http.HttpClient;

@Path("/{any: .*}")
public class ReverseProxy {

    private final HttpRedirectorHandler httpRedirectorHandler; // = HttpRedirectorHandler.getInstance(HttpClient.newBuilder().build());

    @Inject
    public ReverseProxy(HttpRedirectorHandler httpRedirectorHandler) {
        this.httpRedirectorHandler = httpRedirectorHandler;
    }

    @GET
    public Response transferGetRequest(@Context UriInfo uriInfo, @Context HttpServletRequest request) {
        try {
            return httpRedirectorHandler.transferRequest(uriInfo, HttpMethod.valueOf(request.getMethod()));
        } catch (MalformedUriException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Bad URL : " + e.getMessage())
                    .build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Cannot access back service" + e.getMessage())
                    .build();
        }
    }
}