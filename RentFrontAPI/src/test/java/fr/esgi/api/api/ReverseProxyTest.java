package fr.esgi.api.api;

import fr.esgi.api.controller.ReverseProxy;
import fr.esgi.api.exception.MalformedUriException;
import fr.esgi.api.service.HttpRedirectorHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReverseProxyTest {

    @InjectMocks
    private ReverseProxy reverseProxy;

    @Mock
    private UriInfo mockUriInfo;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpRedirectorHandler httpRedirectorHandler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        reverseProxy = new ReverseProxy(httpRedirectorHandler);
    }

    @Test
    public void testTransferGetRequest_Success() throws MalformedUriException {
        // Arrange
        when(request.getMethod()).thenReturn("GET");
        when(httpRedirectorHandler.transferRequest(mockUriInfo, request.getMethod()))
                .thenReturn(Response.status(Response.Status.OK)
                        .entity("Success")
                        .build());

        // Act
        Response response = reverseProxy.transferGetRequest(mockUriInfo, request);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        verifyNoMoreInteractions(httpRedirectorHandler);
    }

    @Test
    public void testTransferGetRequest_MalformedUriException() throws MalformedUriException {
        when(request.getMethod()).thenReturn("GET");
        when(httpRedirectorHandler.transferRequest(mockUriInfo, request.getMethod()))
                .thenThrow(new MalformedUriException("Invalid URI"));

        Response response = reverseProxy.transferGetRequest(mockUriInfo, request);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        verifyNoMoreInteractions(httpRedirectorHandler);
    }

    @Test
    public void testTransferGetRequest_RuntimeException() throws MalformedUriException {
        when(request.getMethod()).thenReturn("GET");
        when(httpRedirectorHandler.transferRequest(mockUriInfo, request.getMethod()))
                .thenThrow(new RuntimeException("Internal server error"));

        Response response = reverseProxy.transferGetRequest(mockUriInfo, request);

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        verifyNoMoreInteractions(httpRedirectorHandler);
    }
}