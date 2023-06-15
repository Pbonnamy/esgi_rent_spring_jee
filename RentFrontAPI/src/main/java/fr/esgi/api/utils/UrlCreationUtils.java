package fr.esgi.api.utils;

import fr.esgi.api.Constants;
import fr.esgi.api.exception.MalformedUriException;
import jakarta.ws.rs.core.UriInfo;

import java.util.Objects;

import static fr.esgi.api.service.HttpRedirectorHandler.*;

public class UrlCreationUtils {

    public static String urlPreparator(String requestUri, String target) {
        if (Objects.equals(target, Constants.PROPERTIES_URI_TARGET)) {
            //SEND REQUEST TO PROPERTIES BACK
            return requestUri.replace(Constants.BASE_FRONT_URI, Constants.BASE_SPRING_URI + Constants.PROPERTIES_URI_TARGET + "/");
        } else if (Objects.equals(target, Constants.CARS_URI_TARGET)) {
            //SEND REQUEST TO CARS BACK
            return requestUri.replace(Constants.BASE_FRONT_URI, Constants.BASE_SPRING_URI + Constants.CARS_URI_TARGET + "/");
        } else {
            throw new MalformedUriException("Error 404 : url not found");
        }
    }

    public static String getBackTarget(String url) throws MalformedUriException {
        try {
            String[] uriParts = url.split(Constants.FRONT_API_URI);
            if (uriParts.length < 2) {
                throw new MalformedUriException("No URL provided: " + url);
            }

            String uri = uriParts[1];
            if ("/".equals(uri) || null == uri) {
                throw new MalformedUriException("No URL provided: " + uri);
            }

            if(Constants.RENTAL_CARS_URI.equals(uri)){
                return Constants.CARS_URI_TARGET;
            }
            else if(Constants.RENTAL_PROPERTIES_URI.equals(uri)){
                return Constants.PROPERTIES_URI_TARGET;
            }
            else {
                throw new MalformedUriException("Bad URL");
            }
        } catch (MalformedUriException e) {
            throw new MalformedUriException("Error in URL", e);
        }
    }

}
