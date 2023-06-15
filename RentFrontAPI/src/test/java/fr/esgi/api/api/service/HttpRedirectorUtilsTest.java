package fr.esgi.api.api.service;

import fr.esgi.api.Constants;
import fr.esgi.api.HttpMethod;
import fr.esgi.api.exception.MalformedUriException;
import fr.esgi.api.service.HttpRedirectorHandler;
import fr.esgi.api.utils.UrlCreationUtils;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HttpRedirectorUtilsTest {
    @Mock
    private UriInfo mockUriInfo;

    private HttpRedirectorHandler httpRedirectorHandler;

    @Mock
    private HttpClient mockedClient;

    @Mock
    private HttpResponse mockedResponse;

    @BeforeEach
    void setUp(){
        httpRedirectorHandler = HttpRedirectorHandler.getInstance(mockedClient);
    }

    @Test
    public void transferRequest_shouldRedirectToPropertiesBack() throws MalformedUriException, IOException, InterruptedException {
        String requestUrl = Constants.BASE_FRONT_URI + Constants.RENTAL_PROPERTIES_URI;

        when(mockedResponse.body()).thenReturn("Success");
        when(mockedClient.send(any(), any())).thenReturn(mockedResponse);
        when(mockUriInfo.getRequestUri()).thenReturn(URI.create(requestUrl));


        Response response = httpRedirectorHandler.transferRequest(mockUriInfo, HttpMethod.GET);


        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("Success", response.getEntity());

        verifyNoMoreInteractions(mockedClient);
    }

    @Test
    public void transferRequest_shouldRedirectToCarsBack() throws MalformedUriException, IOException, InterruptedException {
        String requestUrl = Constants.BASE_FRONT_URI + Constants.RENTAL_CARS_URI;

        when(mockedResponse.body()).thenReturn("Success");
        when(mockedClient.send(any(), any())).thenReturn(mockedResponse);
        when(mockUriInfo.getRequestUri()).thenReturn(URI.create(requestUrl));


        Response response = httpRedirectorHandler.transferRequest(mockUriInfo, HttpMethod.GET);


        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("Success", response.getEntity());

        verifyNoMoreInteractions(mockedClient);
    }


    @Test
    public void transferRequest_shouldThrowMalformedUriException() throws MalformedUriException, IOException, InterruptedException {
        String requestUrl = "http://localhost:3000/front-api";

        when(mockUriInfo.getRequestUri()).thenReturn(URI.create(requestUrl));

        Assertions.assertThrows(MalformedUriException.class, () -> {
            httpRedirectorHandler.transferRequest(mockUriInfo, HttpMethod.GET);
        });
    }

    @Test
    public void transferRequest_shouldThrowRunTimeException() throws MalformedUriException, IOException, InterruptedException {
        String requestUrl = Constants.BASE_FRONT_URI + Constants.RENTAL_PROPERTIES_URI + "unknown";

        when(mockUriInfo.getRequestUri()).thenReturn(URI.create(requestUrl));

        Assertions.assertThrows(RuntimeException.class, () -> {
            httpRedirectorHandler.transferRequest(mockUriInfo, HttpMethod.GET);
        });
    }
}
