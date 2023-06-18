package fr.esgi.api.controller;

import fr.esgi.api.constants.HttpMethod;
import fr.esgi.api.controller.ReverseProxy;
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
public class ReverseProxyPutTest {

    @InjectMocks
    private ReverseProxy reverseProxy;

    @Mock
    private UriInfo mockUriInfo;

    @Mock
    private HttpRedirectorHandler httpRedirectorHandler;

    @Test
    public void testTransferPutRequest_Success() throws MalformedUriException {
        // Arrange
        when(httpRedirectorHandler.transferRequest(mockUriInfo, HttpMethod.PUT, "Body test"))
                .thenReturn(Response.status(Response.Status.OK)
                        .entity("Success")
                        .build());

        // Act
        Response response = reverseProxy.transferPutRequest(mockUriInfo, "Body test");

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        verifyNoMoreInteractions(httpRedirectorHandler);
    }

    @Test
    public void testTransferPutRequest_MalformedUriException() throws MalformedUriException {
        when(httpRedirectorHandler.transferRequest(mockUriInfo, HttpMethod.PUT, "Body test"))
                .thenThrow(new MalformedUriException("Invalid URI"));

        Response response = reverseProxy.transferPutRequest(mockUriInfo,"Body test");

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        verifyNoMoreInteractions(httpRedirectorHandler);
    }

    @Test
    public void testTransferPutRequest_RuntimeException() throws MalformedUriException {
        when(httpRedirectorHandler.transferRequest(mockUriInfo, HttpMethod.PUT, "Body test"))
                .thenThrow(new UnavailableServiceException("Internal server error"));

        Response response = reverseProxy.transferPutRequest(mockUriInfo, "Body test");

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        verifyNoMoreInteractions(httpRedirectorHandler);
    }
}