package fr.esgi.rent.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.esgi.rent.domain.RentalCarEntity;
import fr.esgi.rent.dto.response.RentalCarResponseDto;
import fr.esgi.rent.mapper.RentalCarDtoMapper;
import fr.esgi.rent.service.RentalCarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.List;

import static fr.esgi.rent.samples.RentalCarDtoSample.rentalCarResponseDtos;
import static fr.esgi.rent.samples.RentalCarEntitySample.rentalCarEntities;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RentalCarController.class)
class RentalCarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RentalCarService rentalCarService;

    @MockBean
    private RentalCarDtoMapper rentalCarDtoMapper;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldGetAllRentalCars() throws Exception {
        List<RentalCarEntity> expectedRentalCarEntities = rentalCarEntities();
        List<RentalCarResponseDto> expectedRentalCarResponseDtos = rentalCarResponseDtos();

        when(rentalCarService.findAll()).thenReturn(expectedRentalCarEntities);
        when(rentalCarDtoMapper.toDtoList(expectedRentalCarEntities)).thenReturn(expectedRentalCarResponseDtos);

        mockMvc.perform(get("/rental-cars"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(expectedRentalCarResponseDtos)));

        verify(rentalCarService).findAll();
        verify(rentalCarDtoMapper).toDtoList(expectedRentalCarEntities);
        verifyNoMoreInteractions(rentalCarService, rentalCarDtoMapper);
    }

}