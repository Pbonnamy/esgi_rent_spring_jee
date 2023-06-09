package fr.rent.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.rent.domain.entity.RentPropertyEntity;
import fr.rent.dto.RentPropertyResponseDto;
import fr.rent.mapper.RentPropertyDtoMapper;
import fr.rent.repository.RentPropertyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static fr.rent.samples.RentPropertyDtoSample.oneRentalPropertyResponse;
import static fr.rent.samples.RentPropertyDtoSample.rentalPropertyResponseList;
import static fr.rent.samples.RentPropertyEntitySample.oneRentalPropertyEntity;
import static fr.rent.samples.RentPropertyEntitySample.rentalPropertyEntities;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RentPropertyController.class)
class RentPropertyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RentPropertyRepository rentalPropertyRepository;

    @MockBean
    private RentPropertyDtoMapper rentalPropertyDtoMapper;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldGetRentalProperties() throws Exception {
        List<RentPropertyEntity> rentalPropertyEntities = rentalPropertyEntities();
        List<RentPropertyResponseDto> rentalPropertyResponseList = rentalPropertyResponseList();

        when(rentalPropertyRepository.findAll()).thenReturn(rentalPropertyEntities);
        when(rentalPropertyDtoMapper.mapToDtoList(rentalPropertyEntities)).thenReturn(rentalPropertyResponseList);

        mockMvc.perform(get("/rent-properties-api/rental-properties"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(rentalPropertyResponseList)));

        verify(rentalPropertyRepository).findAll();
        verify(rentalPropertyDtoMapper).mapToDtoList(rentalPropertyEntities);
        verifyNoMoreInteractions(rentalPropertyRepository, rentalPropertyDtoMapper);
    }

    @Test
    void shouldGetRentalPropertyById() throws Exception {
        RentPropertyEntity rentalPropertyEntity = oneRentalPropertyEntity();
        RentPropertyResponseDto rentalPropertyResponseDto = oneRentalPropertyResponse();

        int id = 1;

        when(rentalPropertyRepository.findById(id)).thenReturn(Optional.of(rentalPropertyEntity));
        when(rentalPropertyDtoMapper.mapToDto(rentalPropertyEntity)).thenReturn(rentalPropertyResponseDto);

        mockMvc.perform(get("/rent-properties-api/rental-properties/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(rentalPropertyResponseDto)));

        verify(rentalPropertyRepository).findById(id);
        verify(rentalPropertyDtoMapper).mapToDto(rentalPropertyEntity);
        verifyNoMoreInteractions(rentalPropertyRepository, rentalPropertyDtoMapper);
    }

    @Test
    void givenNoExistentRentalPropertyId_shouldThrowNotFoundRentalPropertyException() throws Exception {

        int id = 1;

        when(rentalPropertyRepository.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/rent-properties-api/rental-properties/{id}", 1))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"message\":\"Impossible de trouver la propriété avec l'id " + id + "\"}"));

        verify(rentalPropertyRepository).findById(id);
        verifyNoInteractions(rentalPropertyDtoMapper);
        verifyNoMoreInteractions(rentalPropertyRepository);
    }

}
