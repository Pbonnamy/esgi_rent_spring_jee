package fr.esgi.rent.api;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;

import java.util.Objects;

@Path("/{any: .*}")
public class ReverseProxy {

    public static final String FRONT_API_URI = "front-api";
    public static final String PROPERTIES_URI = "rent-properties-api";
    public static final String CARS_URI = "rent-cars-api";

    @GET
    public String transferGetRequest(@Context UriInfo uriInfo, @Context HttpServletRequest request) throws Exception {
        return this.transferRequest(uriInfo, request.getMethod());
    }

    @POST
    public String transferPostRequest(@Context UriInfo uriInfo, @Context HttpServletRequest request) throws Exception {
        return this.transferRequest(uriInfo, request.getMethod());
    }

    public String transferRequest(UriInfo uriInfo, String method) throws Exception {
        String url = uriInfo.getRequestUri().toString();

        String target = this.getBackTarget(url);

        if (Objects.equals(target, PROPERTIES_URI)) {
            //SEND REQUEST TO PROPERTIES BACK
            return "sent to properties";
        } else if (Objects.equals(target, CARS_URI)) {
            return "sent to cars";
            //SEND REQUEST TO CARS BACK
        } else {
            return "404";
        }
    }

    private String getBackTarget(String url) throws Exception {
        if (Objects.equals(null, url)) {
            //TODO: Créer mon exception, voir si on peut la faire heriter d'une http exception, sinon try catch au moment de l'appel
            throw new Exception("URL cannot be null");
        }
        String uri = url.split(FRONT_API_URI)[1];

        if ("/".equals(uri) || null == uri) {
            //TODO: créer une excepiton et expliquer dans son message pourquoi
            throw new Exception("Invalid url");
        }
        return uri.split("/")[1];
    }

}