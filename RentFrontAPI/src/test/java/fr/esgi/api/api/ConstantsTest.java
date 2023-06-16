package fr.esgi.api.api;

import fr.esgi.api.constants.Constants;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class ConstantsTest {
    
    @Mock
    Constants constants;

    @Test
    public void testConstantsInitialization() {
        assertEquals("front-api/", Constants.FRONT_API_URI);
        assertEquals("rental-properties", Constants.RENTAL_PROPERTIES_URI);
        assertEquals("rental-cars", Constants.RENTAL_CARS_URI);
        assertEquals("rent-properties-api", Constants.PROPERTIES_URI_TARGET);
        assertEquals("rent-cars-api", Constants.CARS_URI_TARGET);
        assertEquals(3000, Constants.SPRING_PORT);
        assertEquals(8080, Constants.FRONT_PORT);
        assertEquals("http://localhost:", Constants.HOST);
        assertEquals("http://localhost:3000/", Constants.BASE_SPRING_URI);
        assertEquals("http://localhost:8080/front-api/", Constants.BASE_FRONT_URI);
    }

}
