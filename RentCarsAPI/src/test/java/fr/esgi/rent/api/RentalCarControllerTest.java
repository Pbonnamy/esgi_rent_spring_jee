package fr.esgi.rent.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.esgi.rent.domain.RentalCarEntity;
import fr.esgi.rent.dto.request.RentalCarRequestDto;
import fr.esgi.rent.dto.response.RentalCarResponseDto;
import fr.esgi.rent.mapper.RentalCarDtoMapper;
import fr.esgi.rent.repository.RentalCarRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static fr.esgi.rent.samples.RentalCarDtoSample.*;
import static fr.esgi.rent.samples.RentalCarEntitySample.oneRentalCarEntitySample;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import static fr.esgi.rent.samples.RentalCarEntitySample.rentalCarEntitiesSample;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RentalCarController.class)
class RentalCarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RentalCarRepository rentalCarRepository;

    @MockBean
    private RentalCarDtoMapper rentalCarDtoMapper;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldGetAllRentalCars() throws Exception {
        List<RentalCarEntity> expectedRentalCarEntities = rentalCarEntitiesSample();
        List<RentalCarResponseDto> expectedRentalCarResponseDtos = rentalCarResponseDtosSample();

        when(rentalCarRepository.findAll()).thenReturn(expectedRentalCarEntities);
        when(rentalCarDtoMapper.toDtoList(expectedRentalCarEntities)).thenReturn(expectedRentalCarResponseDtos);

        mockMvc.perform(get("/rental-cars"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(expectedRentalCarResponseDtos)));

        verify(rentalCarRepository).findAll();
        verify(rentalCarDtoMapper).toDtoList(expectedRentalCarEntities);
        verifyNoMoreInteractions(rentalCarRepository, rentalCarDtoMapper);
    }

    @Test
    void shouldGetRentalCarById() throws Exception {
        RentalCarEntity expectedRentalCarEntity = oneRentalCarEntitySample();
        RentalCarResponseDto expectedRentalCarResponseDto = oneRentalCarResponseDtoSample();

        Integer id = 1;

        when(rentalCarRepository.findById(id)).thenReturn(Optional.of(expectedRentalCarEntity));
        when(rentalCarDtoMapper.toDto(expectedRentalCarEntity)).thenReturn(expectedRentalCarResponseDto);

        mockMvc.perform(get("/rental-cars/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(expectedRentalCarResponseDto)));


        verify(rentalCarRepository).findById(id);
        verify(rentalCarDtoMapper).toDto(expectedRentalCarEntity);
        verifyNoMoreInteractions(rentalCarRepository, rentalCarDtoMapper);
    }

    @Test
    void givenRentalCarDoesntExist_shouldThrowNotFoundRentalCarException() throws Exception {
        Integer id = 1;

        when(rentalCarRepository.findById(id)).thenReturn(Optional.empty());

        JSONObject expectedJsonResponse = new JSONObject();
        expectedJsonResponse.put("message", "Rental car with id " + id + " not found");

        mockMvc.perform(get("/rental-cars/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(content().string(expectedJsonResponse.toString()));

        verify(rentalCarRepository).findById(id);
        verifyNoMoreInteractions(rentalCarRepository);
        verifyNoInteractions(rentalCarDtoMapper);
    }

    @Test
    void shouldCreateRentalCar() throws Exception {
        RentalCarRequestDto rentalCarRequestDto = oneRentalCarRequestDtoSample();
        RentalCarEntity expectedRentalCarEntity = oneRentalCarEntitySample();

        when(rentalCarDtoMapper.toEntity(rentalCarRequestDto)).thenReturn(expectedRentalCarEntity);
        when(rentalCarRepository.save(expectedRentalCarEntity)).thenReturn(expectedRentalCarEntity);

        mockMvc.perform(post("/rental-cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(rentalCarRequestDto)))
                .andExpect(status().isCreated());

        verify(rentalCarDtoMapper).toEntity(rentalCarRequestDto);
        verify(rentalCarRepository).save(expectedRentalCarEntity);
        verifyNoMoreInteractions(rentalCarRepository, rentalCarDtoMapper);
    }

    @Test
    void givenInvalidRequestBody_shouldNotCreateRentalCar() throws Exception {
        RentalCarRequestDto invalidRentalCarRequestDto = oneInvalidRentalCarRequestDtoSample();

        JSONObject expectedJsonResponse = new JSONObject();
        expectedJsonResponse.put("message", "Invalid request body");

        mockMvc.perform(post("/rental-cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRentalCarRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(expectedJsonResponse.toString()));

        verifyNoInteractions(rentalCarRepository, rentalCarDtoMapper);
    }

    @Test
    void shouldDeleteRentalCar() throws Exception {
        Integer id = 1;

        doNothing().when(rentalCarRepository).deleteById(id);

        mockMvc.perform(delete("/rental-cars/{id}", id))
                .andExpect(status().isNoContent());

        verify(rentalCarRepository).deleteById(id);
        verifyNoMoreInteractions(rentalCarRepository);
    }

}