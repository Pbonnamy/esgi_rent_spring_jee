package fr.esgi.rent.api;

import fr.esgi.rent.exception.MalformedUriException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
    private UriInfo uriInfo;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpRedirectorUtils httpRedirectorUtils;

    @Test
    public void testTransferGetRequest_Success() throws MalformedUriException {
        when(request.getMethod()).thenReturn("GET");
        when(httpRedirectorUtils.transferRequest(uriInfo, request.getMethod()))
                .thenReturn(Response.status(Response.Status.OK)
                        .entity("Success")
                        .build());

        Response response = reverseProxy.transferGetRequest(uriInfo, request);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        verifyNoMoreInteractions(httpRedirectorUtils);
    }

    @Test
    public void testTransferGetRequest_MalformedUriException() throws MalformedUriException {
        when(request.getMethod()).thenReturn("GET");
        when(httpRedirectorUtils.transferRequest(uriInfo, request.getMethod()))
                .thenThrow(new MalformedUriException("Invalid URI"));

        Response response = reverseProxy.transferGetRequest(uriInfo, request);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        verifyNoMoreInteractions(httpRedirectorUtils);
    }

    @Test
    public void testTransferGetRequest_RuntimeException() throws MalformedUriException {
        when(request.getMethod()).thenReturn("GET");
        when(httpRedirectorUtils.transferRequest(uriInfo, request.getMethod()))
                .thenThrow(new RuntimeException("Internal server error"));

        Response response = reverseProxy.transferGetRequest(uriInfo, request);

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        verifyNoMoreInteractions(httpRedirectorUtils);
    }
}