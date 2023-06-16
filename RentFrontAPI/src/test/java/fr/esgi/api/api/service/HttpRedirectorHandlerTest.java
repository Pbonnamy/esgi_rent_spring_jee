package fr.esgi.api.api.service;

import fr.esgi.api.constants.Constants;
import fr.esgi.api.constants.HttpMethod;
import fr.esgi.api.exception.MalformedUriException;
import fr.esgi.api.utils.HttpQueryExecutor;
import fr.esgi.api.service.HttpRedirectorHandler;
import fr.esgi.api.utils.UrlCreationUtils;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HttpRedirectorHandlerTest {
    @Mock
    private UriInfo mockUriInfo;

    @InjectMocks
    private HttpRedirectorHandler httpRedirectorHandler;

    @Mock
    private HttpClient mockedClient;

    @Mock
    private Response mockedResponse;

    @Mock
    private HttpQueryExecutor mockedHttpQueryExecutor;

    @Test
    public void testTransferRequest_ShouldThrowMalformedException() throws MalformedUriException {
        // Arrange
        String requestUri = "http://localhost:3000/front-api";

        when(mockUriInfo.getRequestUri()).thenReturn(URI.create(requestUri));

        // Act & Assert
        Assertions.assertThrows(MalformedUriException.class, () -> {
            httpRedirectorHandler.transferRequest(mockUriInfo, HttpMethod.GET);
        });
        verifyNoMoreInteractions(mockUriInfo, mockedHttpQueryExecutor);
        verify(mockUriInfo).getRequestUri();
    }

    @Test
    public void transferRequest_shouldThrowRunTimeException() throws MalformedUriException, IOException, InterruptedException {
        String requestUrl = Constants.BASE_FRONT_URI + Constants.RENTAL_PROPERTIES_URI + "unknown";

        when(mockUriInfo.getRequestUri()).thenReturn(URI.create(requestUrl));

        Assertions.assertThrows(RuntimeException.class, () -> {
            httpRedirectorHandler.transferRequest(mockUriInfo, HttpMethod.GET);
        });
    }

    @Test
    public void transferRequest_shouldRedirectToPropertiesBack() throws MalformedUriException, IOException, InterruptedException {
        String requestUrl = Constants.BASE_FRONT_URI + Constants.RENTAL_PROPERTIES_URI;

        when(mockUriInfo.getRequestUri()).thenReturn(URI.create(requestUrl));
        when(mockedResponse.getEntity()).thenReturn("Success");
        when(mockedResponse.getStatus()).thenReturn(200);
        when(mockedHttpQueryExecutor.executeQuery(any())).thenReturn(mockedResponse);


        Response response = httpRedirectorHandler.transferRequest(mockUriInfo, HttpMethod.GET);


        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("Success", response.getEntity());

        verifyNoMoreInteractions(mockedClient);
    }

    @Test
    public void transferRequest_shouldRedirectToCarsBack() throws MalformedUriException, IOException, InterruptedException {
        String requestUrl = Constants.BASE_FRONT_URI + Constants.RENTAL_CARS_URI;

        when(mockUriInfo.getRequestUri()).thenReturn(URI.create(requestUrl));
        when(mockedResponse.getEntity()).thenReturn("Success");
        when(mockedResponse.getStatus()).thenReturn(200);
        when(mockedHttpQueryExecutor.executeQuery(any())).thenReturn(mockedResponse);


        Response response = httpRedirectorHandler.transferRequest(mockUriInfo, HttpMethod.GET);


        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("Success", response.getEntity());

        verifyNoMoreInteractions(mockedClient);
    }

    @Test
    public void transferRequest_shouldThrowRunTimeException_when_httpClient_crash() throws MalformedUriException, IOException, InterruptedException {
        String requestUrl = Constants.BASE_FRONT_URI + Constants.RENTAL_CARS_URI;

        when(mockUriInfo.getRequestUri()).thenReturn(URI.create(requestUrl));
        when(mockedHttpQueryExecutor.executeQuery(any())).thenThrow(new RuntimeException());

        Assertions.assertThrows(RuntimeException.class, () -> {
            httpRedirectorHandler.transferRequest(mockUriInfo, HttpMethod.GET);
        });

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
}
