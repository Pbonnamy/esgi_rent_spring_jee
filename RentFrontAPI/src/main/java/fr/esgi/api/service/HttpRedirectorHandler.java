package fr.esgi.api.service;

import fr.esgi.api.constants.Constants;
import fr.esgi.api.constants.HttpMethod;
import fr.esgi.api.exception.BadHttpMethodException;
import fr.esgi.api.exception.MalformedUriException;
import fr.esgi.api.exception.UnavailableServiceException;
import fr.esgi.api.utils.HttpQueryExecutor;
import fr.esgi.api.utils.HttpRequestCreator;
import fr.esgi.api.utils.UrlCreationUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;


@ApplicationScoped
public class HttpRedirectorHandler {

    HttpClient httpClient;
    HttpQueryExecutor httpQueryExecutor;
    HttpRequestCreator httpRequestCreator;

    public HttpRedirectorHandler() {
        this.httpClient = HttpClient.newBuilder().build();
        this.httpQueryExecutor = new HttpQueryExecutor(httpClient);
        this.httpRequestCreator = new HttpRequestCreator();
    }

    public Response transferRequest (UriInfo uriInfo, HttpMethod method) throws MalformedUriException {
        return transferRequest(uriInfo, method, null);
    }

    public Response transferRequest(UriInfo uriInfo, HttpMethod method, String  body) throws MalformedUriException {
        String requestUri = uriInfo.getRequestUri().toString();

        try {
            String target = UrlCreationUtils.getBackTarget(requestUri);
            String url = UrlCreationUtils.urlPreparator(requestUri, target);

            int spring_port = target.equals(Constants.CARS_URI_TARGET) ? Constants.CARS_API_PORT : Constants.PROPERTIES_API_PORT;

            URI uri = UriBuilder.fromUri(url)
                    .port(spring_port)
                    .build();

            HttpRequest httpRequest = httpRequestCreator.create(uri, method, body);

            return httpQueryExecutor.executeQuery(httpRequest);
        } catch (MalformedUriException e) {
            throw new MalformedUriException("Error while trying to parse URL", e);
        } catch (BadHttpMethodException e) {
            throw new BadHttpMethodException("Unknown http method");
        } catch (UnavailableServiceException e) {
            throw new UnavailableServiceException("Cannot access back service", e);
        }
    }
}
