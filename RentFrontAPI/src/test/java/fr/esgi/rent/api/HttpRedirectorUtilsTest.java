package fr.esgi.rent.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import fr.esgi.rent.exception.MalformedUriException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.URI;

@ExtendWith(MockitoExtension.class)
public class HttpRedirectorUtilsTest {
    @Mock
    private UriInfo mockUriInfo;

    private final HttpRedirectorUtils httpRedirectorUtils = new HttpRedirectorUtils();

    @Test
    public void transferRequest_shouldRedirectToPropertiesBack() throws MalformedUriException {
        // Arrange
        String requestUrl = "http://localhost:3000/front-api/rental-properties";
        Response mockedResponse = Response.status(Response.Status.OK).entity("Mocked response").build();

        when(mockUriInfo.getRequestUri()).thenReturn(java.net.URI.create(requestUrl));
        when(httpRedirectorUtils.queryExecutor(URI.create("http://localhost:3000/rent-properties-api"), "GET"))
                .thenReturn(mockedResponse);


        // Act
        Response response = httpRedirectorUtils.transferRequest(mockUriInfo, "GET");

        // Assert
        assertNotNull(response);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assertions.assertEquals("Mocked response", response.getEntity());
        // Add more assertions as needed
    }


    @Test
    public void testTransferRequest_Success() throws MalformedUriException {
        // Arrange
        UriInfo uriInfo = mock(UriInfo.class);
        when(uriInfo.getRequestUri()).thenReturn(URI.create("http://localhost:3000/front-api/rental-properties"));

        HttpRedirectorUtils mockedHttpRedirectorUtils = Mockito.spy(httpRedirectorUtils);

        // Mock the queryExecutor method
        Response mockedResponse = Response.status(Response.Status.OK).entity("Mocked response").build();
        Mockito.doReturn(mockedResponse)
                .when(mockedHttpRedirectorUtils)
                .queryExecutor(URI.create("http://localhost:3000/rent-properties-api"), "GET");

        // Act
        Response response = mockedHttpRedirectorUtils.transferRequest(uriInfo, "GET");

        // Assert
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assertions.assertEquals("Mocked response", response.getEntity());
        // Add additional assertions based on the expected behavior
    }

}
