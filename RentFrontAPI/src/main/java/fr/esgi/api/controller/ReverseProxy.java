package fr.esgi.api.controller;


import fr.esgi.api.constants.HttpMethod;
import fr.esgi.api.exception.MalformedUriException;
import fr.esgi.api.exception.UnavailableServiceException;
import fr.esgi.api.service.HttpRedirectorHandler;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("/{any: .*}")
public class ReverseProxy {

    private final HttpRedirectorHandler httpRedirectorHandler;

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
        } catch (UnavailableServiceException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Cannot access back service" + e.getMessage())
                    .build();
        }
    }

    @POST
    public Response transferPostRequest(@Context UriInfo uriInfo, String body) {
        try {
            return httpRedirectorHandler.transferRequest(uriInfo, HttpMethod.POST, body);
        } catch (MalformedUriException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Bad URL : " + e.getMessage())
                    .build();
        } catch (UnavailableServiceException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Cannot access back service" + e.getMessage())
                    .build();
        }
    }

    @PUT
    public Response transferPutRequest(@Context UriInfo uriInfo, String body) {
        try {
            return httpRedirectorHandler.transferRequest(uriInfo, HttpMethod.PUT, body);
        } catch (MalformedUriException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Bad URL : " + e.getMessage())
                    .build();
        } catch (UnavailableServiceException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Cannot access back service" + e.getMessage())
                    .build();
        }
    }

    @PATCH
    public Response transferPatchRequest(@Context UriInfo uriInfo, String body) {
        try {
            return httpRedirectorHandler.transferRequest(uriInfo, HttpMethod.PATCH, body);
        } catch (MalformedUriException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Bad URL : " + e.getMessage())
                    .build();
        } catch (UnavailableServiceException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Cannot access back service" + e.getMessage())
                    .build();
        }
    }

    @DELETE
    public Response transferDeleteRequest(@Context UriInfo uriInfo) {
        try {
            return httpRedirectorHandler.transferRequest(uriInfo, HttpMethod.DELETE);
        } catch (MalformedUriException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Bad URL : " + e.getMessage())
                    .build();
        } catch (UnavailableServiceException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Cannot access back service" + e.getMessage())
                    .build();
        }
    }
}