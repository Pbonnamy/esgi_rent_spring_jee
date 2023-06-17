package fr.esgi.api.api.utils;

import fr.esgi.api.utils.HttpQueryExecutor;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HttpQueryExecutorTest {

    @Mock
    HttpResponse mockedHttpResponse;

    @Test
    public void testExecuteQuery() throws Exception {
        HttpClient mockClient = Mockito.mock(HttpClient.class);

        when(mockedHttpResponse.body()).thenReturn("Mocked response body");
        when(mockedHttpResponse.statusCode()).thenReturn(200);
        when(mockClient.send(Mockito.any(HttpRequest.class), Mockito.any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockedHttpResponse);

        HttpQueryExecutor httpQueryExecutor = new HttpQueryExecutor(mockClient);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost.com"))
                .build();

        Response mockedResponse = Response.status(mockedHttpResponse.statusCode())
                .entity(mockedHttpResponse.body())
                .build();

        Response response = httpQueryExecutor.executeQuery(request);


        assertEquals(mockedResponse.getStatus(), response.getStatus());
        assertEquals(200, response.getStatus());
    }

    @Test
    public void testExecuteQuery_throw_Exception() throws Exception {
        HttpClient mockClient = Mockito.mock(HttpClient.class);

        when(mockClient.send(Mockito.any(HttpRequest.class), Mockito.any(HttpResponse.BodyHandler.class)))
                .thenThrow(new RuntimeException("Error"));

        HttpQueryExecutor httpQueryExecutor = new HttpQueryExecutor(mockClient);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost.com"))
                .build();

        Assertions.assertThrows(RuntimeException.class, () -> {
            httpQueryExecutor.executeQuery(request);
        });
    }
}
