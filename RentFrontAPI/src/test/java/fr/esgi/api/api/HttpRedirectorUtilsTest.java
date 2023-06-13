package fr.esgi.api.api;

import fr.esgi.api.exception.MalformedUriException;
import fr.esgi.api.service.HttpRedirectorHandler;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HttpRedirectorUtilsTest {
    @Mock
    private UriInfo mockUriInfo;

    private HttpRedirectorHandler httpRedirectorHandler;

    private HttpClient mockedClient;

    private HttpResponse mockedResponse;

    @BeforeEach
    public void setup() {
        mockedClient = mock(HttpClient.class);
        httpRedirectorHandler = HttpRedirectorHandler.getInstance(mockedClient);
        mockedResponse = mock(HttpResponse.class);
    }

    @AfterEach
    public void cleanup() {
        mockedClient = null;
        httpRedirectorHandler = null;
        mockedResponse = null;
    }

    @Test
    public void transferRequest_shouldRedirectToPropertiesBack() throws MalformedUriException, IOException, InterruptedException {
        httpRedirectorHandler = HttpRedirectorHandler.getInstance(mockedClient);

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

    @Test
    public void transferRequest_shouldRedirectToGoodUrl() throws MalformedUriException, IOException, InterruptedException {
        httpRedirectorHandler = HttpRedirectorHandler.getInstance(mockedClient);

        String requestUrl = HttpRedirectorHandler.BASE_FRONT_URL + "/" + HttpRedirectorHandler.RENTAL_PROPERTIES_FRONT_URI;
        String expectedUrl = HttpRedirectorHandler.BASE_SPRING_URL + "/" + HttpRedirectorHandler.PROPERTIES_URI_TARGET + "/" + HttpRedirectorHandler.RENTAL_PROPERTIES_FRONT_URI;
        HttpRequest expectedRequest = HttpRequest.newBuilder()
                .uri(URI.create(expectedUrl))
                .GET()
                .build();

        when(mockedResponse.body()).thenReturn("Good uri");
        when(mockedClient.send(eq(expectedRequest), any())).thenReturn(mockedResponse);
        when(mockUriInfo.getRequestUri()).thenReturn(URI.create(requestUrl));

        Response response = httpRedirectorHandler.transferRequest(mockUriInfo, "GET");

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("Good uri", response.getEntity());

        verifyNoMoreInteractions(mockedClient);
    }

}
