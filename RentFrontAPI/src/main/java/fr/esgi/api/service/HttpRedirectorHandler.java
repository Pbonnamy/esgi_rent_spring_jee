package fr.esgi.api.service;

import fr.esgi.api.Constants;
import fr.esgi.api.HttpMethod;
import fr.esgi.api.exception.MalformedUriException;
import fr.esgi.api.utils.HttpQueryExecutor;
import fr.esgi.api.utils.HttpRequestCreator;
import fr.esgi.api.utils.UrlCreationUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.net.http.HttpRequest;


@ApplicationScoped
public class HttpRedirectorHandler {

    public Response transferRequest(UriInfo uriInfo, HttpMethod method) throws MalformedUriException {
        String requestUri = uriInfo.getRequestUri().toString();

        try {
            String target = UrlCreationUtils.getBackTarget(requestUri);
            HttpQueryExecutor httpQueryExecutor = new HttpQueryExecutor();
            HttpRequestCreator httpRequestCreator = new HttpRequestCreator();
            String url = UrlCreationUtils.urlPreparator(requestUri, target);

            URI uri = UriBuilder.fromUri(url)
                    .port(Constants.SPRING_PORT)
                    .build();

            HttpRequest httpRequest = httpRequestCreator.create(uri, method);

            return httpQueryExecutor.executeQuery(httpRequest);
        } catch (MalformedUriException e) {
            throw new MalformedUriException("Error while trying to parse URL", e);
        } catch (RuntimeException e) {
            throw new RuntimeException("Cannot access back service", e);
        }
    }
}
