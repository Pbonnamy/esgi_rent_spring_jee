package fr.esgi.api.service;

import fr.esgi.api.constants.Constants;
import fr.esgi.api.constants.HttpMethod;
import fr.esgi.api.exception.BadHttpMethodException;
import fr.esgi.api.exception.MalformedUriException;
import fr.esgi.api.exception.UnavailableServiceException;
import fr.esgi.api.service.HttpRedirectorHandler;
import fr.esgi.api.utils.HttpQueryExecutor;
import fr.esgi.api.utils.HttpRequestCreator;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

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

    @Mock
    private HttpRequestCreator httpRequestCreator;

    @Test
    public void testTransferRequest_InvalidRequestUri_ShouldThrowMalformedUriException() throws MalformedUriException {
        String requestUri = "http://localhost:3000/front-api";

        when(mockUriInfo.getRequestUri()).thenReturn(URI.create(requestUri));

        Assertions.assertThrows(MalformedUriException.class, () -> {
            httpRedirectorHandler.transferRequest(mockUriInfo, HttpMethod.GET);
        }, "Expected MalformedUriException to be thrown");

        verify(mockUriInfo).getRequestUri();
        verifyNoMoreInteractions(mockUriInfo, mockedHttpQueryExecutor);
    }


    @Test
    public void transferRequest_shouldThrowRunTimeException() throws MalformedUriException {
        String requestUrl = Constants.BASE_FRONT_URI + Constants.RENTAL_PROPERTIES_URI;

        when(mockUriInfo.getRequestUri()).thenReturn(URI.create(requestUrl));
        when(mockedHttpQueryExecutor.executeQuery(any())).thenThrow(new UnavailableServiceException("Error"));

        Assertions.assertThrows(UnavailableServiceException.class, () -> {
            httpRedirectorHandler.transferRequest(mockUriInfo, HttpMethod.GET);
        }, "Expected UnavailableServiceException to be thrown");

        verify(mockUriInfo).getRequestUri();
        verify(mockedHttpQueryExecutor).executeQuery(any());

        verifyNoMoreInteractions(mockedClient, mockUriInfo, mockedHttpQueryExecutor);
    }

    @Test
    public void transferRequest_shouldThrowBadHttpMethodException() throws BadHttpMethodException {
        String requestUrl = Constants.BASE_FRONT_URI + Constants.RENTAL_PROPERTIES_URI;

        when(mockUriInfo.getRequestUri()).thenReturn(URI.create(requestUrl));
        when(httpRequestCreator.create(any(), any(), any())).thenThrow(BadHttpMethodException.class);


        Assertions.assertThrows(BadHttpMethodException.class, () -> {
            Response response = httpRedirectorHandler.transferRequest(mockUriInfo, null);
                }, "Expected BadHttpMethodException to be thrown");

        verify(mockUriInfo).getRequestUri();
        verify(httpRequestCreator).create(any(), any(), any());

        verifyNoMoreInteractions(mockedClient, mockUriInfo, mockedHttpQueryExecutor);
    }

    @Test
    public void transferRequest_shouldRedirectToPropertiesBack() throws MalformedUriException {
        String requestUrl = Constants.BASE_FRONT_URI + Constants.RENTAL_PROPERTIES_URI;

        when(mockUriInfo.getRequestUri()).thenReturn(URI.create(requestUrl));
        when(mockedResponse.getEntity()).thenReturn("Success");
        when(mockedResponse.getStatus()).thenReturn(200);
        when(mockedHttpQueryExecutor.executeQuery(any())).thenReturn(mockedResponse);


        Response response = httpRedirectorHandler.transferRequest(mockUriInfo, HttpMethod.GET);


        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("Success", response.getEntity());

        verify(mockUriInfo).getRequestUri();
        verify(mockedHttpQueryExecutor).executeQuery(any());

        verifyNoMoreInteractions(mockedClient, mockUriInfo, mockedHttpQueryExecutor);
    }

    @Test
    public void transferRequest_shouldRedirectToCarsBack() throws MalformedUriException {
        String requestUrl = Constants.BASE_FRONT_URI + Constants.RENTAL_CARS_URI;

        when(mockUriInfo.getRequestUri()).thenReturn(URI.create(requestUrl));
        when(mockedResponse.getEntity()).thenReturn("Success");
        when(mockedResponse.getStatus()).thenReturn(200);
        when(mockedHttpQueryExecutor.executeQuery(any())).thenReturn(mockedResponse);


        Response response = httpRedirectorHandler.transferRequest(mockUriInfo, HttpMethod.GET);


        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("Success", response.getEntity());

        verify(mockUriInfo).getRequestUri();
        verify(mockedHttpQueryExecutor).executeQuery(any());

        verifyNoMoreInteractions(mockedClient, mockUriInfo, mockedHttpQueryExecutor);
    }

    @Test
    public void transferRequest_shouldThrowRunTimeException_when_httpClient_crash() throws MalformedUriException {
        String requestUrl = Constants.BASE_FRONT_URI + Constants.RENTAL_CARS_URI;

        when(mockUriInfo.getRequestUri()).thenReturn(URI.create(requestUrl));
        when(mockedHttpQueryExecutor.executeQuery(any())).thenThrow(new RuntimeException());

        Assertions.assertThrows(RuntimeException.class, () -> {
            httpRedirectorHandler.transferRequest(mockUriInfo, HttpMethod.GET);
        });

        verify(mockUriInfo).getRequestUri();
        verify(mockedHttpQueryExecutor).executeQuery(any());

        verifyNoMoreInteractions(mockedClient, mockUriInfo, mockedHttpQueryExecutor);
    }


    @Test
    public void transferRequest_shouldThrowMalformedUriException() throws MalformedUriException {
        String requestUrl = "http://localhost:3000/front-api";

        when(mockUriInfo.getRequestUri()).thenReturn(URI.create(requestUrl));

        Assertions.assertThrows(MalformedUriException.class, () -> {
            httpRedirectorHandler.transferRequest(mockUriInfo, HttpMethod.GET);
        });

        verify(mockUriInfo).getRequestUri();

        verifyNoMoreInteractions(mockUriInfo, mockedHttpQueryExecutor);
    }
}
