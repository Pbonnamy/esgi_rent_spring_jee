package fr.esgi.api.api.controller;

import fr.esgi.api.constants.HttpMethod;
import fr.esgi.api.controller.ReverseProxy;
import fr.esgi.api.exception.MalformedUriException;
import fr.esgi.api.service.HttpRedirectorHandler;
import jakarta.servlet.http.HttpServletRequest;
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
public class ReverseProxyPatchTest {

    @InjectMocks
    private ReverseProxy reverseProxy;

    @Mock
    private UriInfo mockUriInfo;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpRedirectorHandler httpRedirectorHandler;

    @Test
    public void testTransferPatchRequest_Success() throws MalformedUriException {
        // Arrange
        when(request.getMethod()).thenReturn("PATCH");
        when(httpRedirectorHandler.transferRequest(mockUriInfo, HttpMethod.valueOf(request.getMethod()), "Body test"))
                .thenReturn(Response.status(Response.Status.OK)
                        .entity("Success")
                        .build());

        // Act
        Response response = reverseProxy.transferPatchRequest(mockUriInfo, request, "Body test");

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        verifyNoMoreInteractions(httpRedirectorHandler);
    }

    @Test
    public void testTransferPatchRequest_MalformedUriException() throws MalformedUriException {
        when(request.getMethod()).thenReturn(String.valueOf(HttpMethod.PATCH));
        when(httpRedirectorHandler.transferRequest(mockUriInfo, HttpMethod.valueOf(request.getMethod()), "Body test"))
                .thenThrow(new MalformedUriException("Invalid URI"));

        Response response = reverseProxy.transferPatchRequest(mockUriInfo, request, "Body test");

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        verifyNoMoreInteractions(httpRedirectorHandler);
    }

    @Test
    public void testTransferPatchRequest_RuntimeException() throws MalformedUriException {
        when(request.getMethod()).thenReturn(String.valueOf(HttpMethod.PATCH));
        when(httpRedirectorHandler.transferRequest(mockUriInfo, HttpMethod.valueOf(request.getMethod()), "Body test"))
                .thenThrow(new RuntimeException("Internal server error"));

        Response response = reverseProxy.transferPatchRequest(mockUriInfo, request, "Body test");

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        verifyNoMoreInteractions(httpRedirectorHandler);
    }
}