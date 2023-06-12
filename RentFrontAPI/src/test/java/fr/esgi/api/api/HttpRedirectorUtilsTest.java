package fr.esgi.api.api;

import fr.esgi.api.exception.MalformedUriException;
import fr.esgi.api.service.HttpRedirectorHandler;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HttpRedirectorUtilsTest {
    @Mock
    private UriInfo mockUriInfo;

    private HttpRedirectorHandler httpRedirectorHandler;

    @Test
    public void transferRequest_shouldRedirectToPropertiesBack() throws MalformedUriException, IOException, InterruptedException {
        HttpClient mockedClient = mock(HttpClient.class);
        httpRedirectorHandler = HttpRedirectorHandler.getInstance(mockedClient);
        HttpResponse mockedResponse = Mockito.mock(HttpResponse.class);
        String requestUrl = "http://localhost:3000/front-api/rental-properties";

        when(mockedResponse.body()).thenReturn("Success");
        when(mockedClient.send(any(), any())).thenReturn((mockedResponse));
        when(mockUriInfo.getRequestUri()).thenReturn(URI.create(requestUrl));


        Response response = httpRedirectorHandler.transferRequest(mockUriInfo, "GET");


        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("Success", response.getEntity());
    }

    @Test
    public void transferRequest_shouldThrowMalformedUriException() throws MalformedUriException, IOException, InterruptedException {
        HttpClient mockedClient = mock(HttpClient.class);
        httpRedirectorHandler = HttpRedirectorHandler.getInstance(mockedClient);
        String requestUrl = "http://localhost:3000/front-api";

        when(mockUriInfo.getRequestUri()).thenReturn(URI.create(requestUrl));

        Assertions.assertThrows(MalformedUriException.class, () -> {
            httpRedirectorHandler.transferRequest(mockUriInfo, "GET");
        });
    }

    @Test
    public void transferRequest_shouldThrowRunTimeException() throws MalformedUriException, IOException, InterruptedException {
        HttpClient mockedClient = mock(HttpClient.class);
        httpRedirectorHandler = HttpRedirectorHandler.getInstance(mockedClient);
        String requestUrl = "http://localhost:3000/front-api/unknown";

        when(mockUriInfo.getRequestUri()).thenReturn(URI.create(requestUrl));

        Assertions.assertThrows(RuntimeException.class, () -> {
            httpRedirectorHandler.transferRequest(mockUriInfo, "GET");
        });
    }

}
