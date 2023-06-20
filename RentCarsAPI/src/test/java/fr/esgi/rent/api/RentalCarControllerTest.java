package fr.esgi.rent.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.esgi.rent.domain.RentalCarEntity;
import fr.esgi.rent.dto.request.RentalCarRequestDto;
import fr.esgi.rent.dto.request.SingleFieldRentalCarRequestDto;
import fr.esgi.rent.dto.response.RentalCarResponseDto;
import fr.esgi.rent.interceptor.AuthInterceptor;
import fr.esgi.rent.mapper.RentalCarDtoMapper;
import fr.esgi.rent.repository.RentalCarRepository;
import fr.esgi.rent.service.RentalCarService;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
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

    @MockBean
    private RentalCarService rentalCarService;

    @MockBean
    private AuthInterceptor authInterceptor;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void beforeEach() throws Exception {
        when(authInterceptor.preHandle(any(), any(), any())).thenReturn(true);
    }

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

    @Test
    void shouldUpdateRentalCar() throws Exception {
        int id = 1;

        RentalCarRequestDto rentalCarRequestDto = oneRentalCarRequestDtoSample();
        RentalCarEntity expectedRentalCarEntity = oneRentalCarEntitySample();

        when(rentalCarDtoMapper.toEntity(rentalCarRequestDto)).thenReturn(expectedRentalCarEntity);
        doNothing().when(rentalCarService).updateRentalCar(expectedRentalCarEntity, id);

        mockMvc.perform(put("/rental-cars/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(rentalCarRequestDto)))
                .andExpect(status().isOk());

        verify(rentalCarDtoMapper).toEntity(rentalCarRequestDto);
        verify(rentalCarService).updateRentalCar(expectedRentalCarEntity, id);
        verifyNoMoreInteractions(rentalCarDtoMapper, rentalCarService);
    }

    @Test
    void givenInvalidRequestBody_shouldNotUpdateRentalCar() throws Exception {
        int id = 1;

        RentalCarRequestDto invalidRentalCarRequestDto = oneInvalidRentalCarRequestDtoSample();

        JSONObject expectedJsonResponse = new JSONObject();
        expectedJsonResponse.put("message", "Invalid request body");

        mockMvc.perform(put("/rental-cars/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRentalCarRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(expectedJsonResponse.toString()));

        verifyNoInteractions(rentalCarDtoMapper, rentalCarService);
    }

    @Test
    void shouldUpdateRentalCarPartially() throws Exception {
        int id = 1;

        SingleFieldRentalCarRequestDto singleFieldRentalCarRequestDto = oneSingleFieldRentalCarRequestDtoSample();
        RentalCarEntity expectedRentalCarEntity = oneRentalCarEntitySample();

        when(rentalCarRepository.findById(id)).thenReturn(Optional.of(expectedRentalCarEntity));
        doNothing().when(rentalCarService).updateRentalCarPartially(expectedRentalCarEntity, singleFieldRentalCarRequestDto.rentAmount());

        mockMvc.perform(patch("/rental-cars/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(singleFieldRentalCarRequestDto)))
                .andExpect(status().isOk());

        verify(rentalCarRepository).findById(id);
        verify(rentalCarService).updateRentalCarPartially(expectedRentalCarEntity, singleFieldRentalCarRequestDto.rentAmount());
        verifyNoMoreInteractions(rentalCarRepository, rentalCarService);
    }

    @Test
    void givenRentalCarDoesntExist_shouldNotUpdatePartiallyRentalCar() throws Exception {
        int id = 1;

        SingleFieldRentalCarRequestDto singleFieldRentalCarRequestDto = oneSingleFieldRentalCarRequestDtoSample();

        when(rentalCarRepository.findById(id)).thenReturn(Optional.empty());

        JSONObject expectedJsonResponse = new JSONObject();
        expectedJsonResponse.put("message", "Rental car with id " + id + " not found");

        mockMvc.perform(patch("/rental-cars/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(singleFieldRentalCarRequestDto)))
                .andExpect(status().isNotFound())
                .andExpect(content().string(expectedJsonResponse.toString()));

        verify(rentalCarRepository).findById(id);
        verifyNoMoreInteractions(rentalCarRepository);
        verifyNoInteractions(rentalCarService);
    }


    @Test
    void givenInvalidRequestBody_shouldNotUpdatePartiallyRentalCar() throws Exception {
        int id = 1;

        SingleFieldRentalCarRequestDto invalidSingleFieldRentalCarRequestDto = oneInvalidSingleFieldRentalCarRequestDtoSample();

        JSONObject expectedJsonResponse = new JSONObject();
        expectedJsonResponse.put("message", "Invalid request body");

        mockMvc.perform(patch("/rental-cars/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidSingleFieldRentalCarRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(expectedJsonResponse.toString()));

        verifyNoInteractions(rentalCarRepository, rentalCarDtoMapper);
    }
}