package fr.esgi.api.constants;

import fr.esgi.api.constants.Constants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ConstantsTest {
    @Test
    public void testConstantsInitialization() {
        assertEquals("front-api/", Constants.FRONT_API_URI);
        assertEquals("rental-properties", Constants.RENTAL_PROPERTIES_URI);
        assertEquals("rental-cars", Constants.RENTAL_CARS_URI);
        assertEquals("rent-properties-api", Constants.PROPERTIES_URI_TARGET);
        assertEquals("rent-cars-api", Constants.CARS_URI_TARGET);
        assertEquals(3000, Constants.CARS_API_PORT);
        assertEquals(3001, Constants.PROPERTIES_API_PORT);
        assertEquals(8080, Constants.FRONT_PORT);
        assertEquals("http://localhost:", Constants.HOST);
        assertEquals("http://localhost:3000/", Constants.BASE_CARS_API_URI);
        assertEquals("http://localhost:3001/", Constants.BASE_PROPERTIES_API_URI);
        assertEquals("http://localhost:8080/front-api/", Constants.BASE_FRONT_URI);
    }

}
