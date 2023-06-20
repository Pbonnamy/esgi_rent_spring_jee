package fr.esgi.api.controller;

import fr.esgi.api.constants.HttpMethod;
import fr.esgi.api.exception.MalformedUriException;
import fr.esgi.api.exception.UnavailableServiceException;
import fr.esgi.api.service.HttpRedirectorHandler;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReverseProxyGetTest {

    @InjectMocks
    private ReverseProxy reverseProxy;

    @Mock
    private UriInfo mockUriInfo;

    @Mock
    private HttpRedirectorHandler httpRedirectorHandler;

    @Test
    public void testTransferGetRequest_Success() throws MalformedUriException {
        // Arrange
        when(httpRedirectorHandler.transferRequest(mockUriInfo, HttpMethod.GET))
                .thenReturn(Response.status(Response.Status.OK)
                        .entity("Success")
                        .build());

        // Act
        Response response = reverseProxy.transferGetRequest(mockUriInfo);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("Success", response.getEntity());

        verifyNoMoreInteractions(httpRedirectorHandler);
    }

    @Test
    public void testTransferGetRequest_MalformedUriException() throws MalformedUriException {
        when(httpRedirectorHandler.transferRequest(mockUriInfo, HttpMethod.GET))
                .thenThrow(new MalformedUriException("Invalid URI"));

        Response response = reverseProxy.transferGetRequest(mockUriInfo);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals("Bad URL : Invalid URI", response.getEntity());

        verifyNoMoreInteractions(httpRedirectorHandler);
    }

    @Test
    public void testTransferGetRequest_UnaviableServiceException() throws MalformedUriException {
        when(httpRedirectorHandler.transferRequest(mockUriInfo, HttpMethod.GET))
                .thenThrow(new UnavailableServiceException("Internal server error"));

        Response response = reverseProxy.transferGetRequest(mockUriInfo);

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        assertEquals("Cannot access back service", response.getEntity());

        verifyNoMoreInteractions(httpRedirectorHandler);
    }
}