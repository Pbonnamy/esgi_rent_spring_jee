package fr.esgi.rent.api;


import fr.esgi.rent.exception.MalformedUriException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("/{any: .*}")
public class ReverseProxy {

    HttpRedirectorUtils httpRedirectorUtils = new HttpRedirectorUtils();

    @GET
    public Response transferGetRequest(@Context UriInfo uriInfo, @Context HttpServletRequest request) {
        try {
            return httpRedirectorUtils.transferRequest(uriInfo, request.getMethod());
        } catch (MalformedUriException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Bad URL : " + e.getMessage())
                    .build();
        }
        catch (RuntimeException e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Cannot access back service" + e.getMessage())
                    .build();
        }
    }
}