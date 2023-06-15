package fr.esgi.api.service;

import fr.esgi.api.Constants;
import fr.esgi.api.HttpMethod;
import fr.esgi.api.exception.MalformedUriException;
import fr.esgi.api.utils.UrlCreationUtils;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpRedirectorHandler {
    private static HttpRedirectorHandler instance;
    private final HttpClient client;

    public HttpRedirectorHandler(HttpClient client) {
        this.client = client;
    }

    public static HttpRedirectorHandler getInstance(HttpClient value) {
        if (instance == null) {
            instance = new HttpRedirectorHandler(value);
        }
        return instance;
    }

    Response httpQueryRedirection(UriInfo uriInfo, HttpMethod method, String target) {
        try {
            String requestUri = String.valueOf(uriInfo.getRequestUri());
            String url = UrlCreationUtils.urlPreparator(requestUri, target);

            URI uri = UriBuilder.fromUri(url)
                    .port(Constants.SPRING_PORT)
                    .build();

            return this.queryExecutor(uri, method);

        } catch (MalformedUriException e) {
            throw new MalformedUriException("Url is not provided by any service");
        } catch (RuntimeException e) {
            throw new RuntimeException("Cannot access back services", e);
        }
    }

    private Response queryExecutor(URI uri, HttpMethod method) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();
            HttpResponse<String> httpResponse = this.client.send(request, HttpResponse.BodyHandlers.ofString());

            return Response.status(Response.Status.OK)
                    .entity(httpResponse.body())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Response transferRequest(UriInfo uriInfo, HttpMethod method) throws MalformedUriException {
        String url = uriInfo.getRequestUri().toString();

        try {
            String target = UrlCreationUtils.getBackTarget(url);
            return this.httpQueryRedirection(uriInfo, method, target);
        } catch (MalformedUriException e) {
            throw new MalformedUriException("Error while trying to parse URL", e);
        } catch (RuntimeException e) {
            throw new RuntimeException("Cannot access back service", e);
        }
    }
}
