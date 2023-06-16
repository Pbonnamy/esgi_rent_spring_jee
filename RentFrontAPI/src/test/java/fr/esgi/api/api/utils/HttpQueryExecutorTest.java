package fr.esgi.api.api.utils;

import fr.esgi.api.Constants;
import fr.esgi.api.HttpMethod;
import fr.esgi.api.exception.MalformedUriException;
import fr.esgi.api.service.HttpRedirectorHandler;
import fr.esgi.api.utils.HttpQueryExecutor;
import fr.esgi.api.utils.HttpRequestCreator;
import fr.esgi.api.utils.UrlCreationUtils;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HttpQueryExecutorTest {

    @Mock
    HttpResponse mockedHttpResponse;

    @Test
    public void testExecuteQuery() throws Exception {
        HttpClient mockClient = Mockito.mock(HttpClient.class);

        when(mockedHttpResponse.body()).thenReturn("Mocked response body");
        when(mockedHttpResponse.statusCode()).thenReturn(200);
        when(mockClient.send(Mockito.any(HttpRequest.class), Mockito.any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockedHttpResponse);

        HttpQueryExecutor httpQueryExecutor = new HttpQueryExecutor(mockClient);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost.com"))
                .build();

        Response response = httpQueryExecutor.executeQuery(request);

        Response mockedResponse = Response.status(mockedHttpResponse.statusCode())
                .entity(mockedHttpResponse.body())
                .build();

        assertEquals(mockedResponse.getStatus(), response.getStatus());
        assertEquals(200, response.getStatus());
    }
}
