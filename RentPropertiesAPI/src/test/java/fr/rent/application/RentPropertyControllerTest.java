package fr.rent.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.rent.domain.entity.RentPropertyEntity;
import fr.rent.dto.RentPropertyRequestDto;
import fr.rent.dto.RentPropertyResponseDto;
import fr.rent.mapper.RentPropertyDtoMapper;
import fr.rent.repository.RentPropertyRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static fr.rent.samples.RentPropertyDtoSample.*;
import static fr.rent.samples.RentPropertyEntitySample.oneRentalPropertyEntity;
import static fr.rent.samples.RentPropertyEntitySample.rentalPropertyEntities;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RentPropertyController.class)
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

        mockMvc.perform(get("/rental-properties"))
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

        mockMvc.perform(get("/rental-properties/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(rentalPropertyResponseDto)));

        verify(rentalPropertyRepository).findById(id);
        verify(rentalPropertyDtoMapper).mapToDto(rentalPropertyEntity);
        verifyNoMoreInteractions(rentalPropertyRepository, rentalPropertyDtoMapper);
    }

    @Test
    void givenNoExistentRentalPropertyId_shouldThrowNotFoundRentalPropertyException() throws Exception {

        int id = 1;
        JSONObject expectedJsonResponse = new JSONObject();
        expectedJsonResponse.put("message", "Impossible de trouver la propriété avec l'id " + id);

        when(rentalPropertyRepository.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/rental-properties/{id}", 1))
                .andExpect(status().isNotFound())
                .andExpect(content().json(expectedJsonResponse.toString()));

        verify(rentalPropertyRepository).findById(id);
        verifyNoInteractions(rentalPropertyDtoMapper);
        verifyNoMoreInteractions(rentalPropertyRepository);
    }

    @Test
    void shouldCreateRentalProperty() throws Exception {
        RentPropertyRequestDto rentalPropertyRequestDto = oneRentalPropertyRequest();
        RentPropertyResponseDto rentalPropertyResponseDto = oneRentalPropertyResponse();
        RentPropertyEntity rentalPropertyEntity = oneRentalPropertyEntity();

        when(rentalPropertyDtoMapper.mapToEntity(rentalPropertyRequestDto)).thenReturn(rentalPropertyEntity);
        when(rentalPropertyRepository.save(rentalPropertyEntity)).thenReturn(rentalPropertyEntity);
        when(rentalPropertyDtoMapper.mapToDto(rentalPropertyEntity)).thenReturn(rentalPropertyResponseDto);

        mockMvc.perform(post("/rental-properties")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(rentalPropertyRequestDto)))
                .andExpect(status().isCreated());

        verify(rentalPropertyDtoMapper).mapToEntity(rentalPropertyRequestDto);
        verify(rentalPropertyRepository).save(rentalPropertyEntity);
        verifyNoMoreInteractions(rentalPropertyDtoMapper, rentalPropertyRepository);
    }

    @Test
    void givenInvalidRequestBody_shouldReturn404HttpStatusCode() throws Exception {

        RentPropertyRequestDto invalidRequest = oneRentalPropertyRequestWithInvalidValue();
        JSONObject expectedJsonResponse = new JSONObject();
        expectedJsonResponse.put("message", "L'un des champs est manquant ou incorrect");

        mockMvc.perform(post("/rental-properties")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(expectedJsonResponse.toString()));

        verifyNoInteractions(rentalPropertyDtoMapper, rentalPropertyRepository);
    }

    @Test
    void shouldUpdateRentalProperty() throws Exception {
        RentPropertyRequestDto rentalPropertyRequestDto = oneRentalPropertyRequest();
        RentPropertyResponseDto rentalPropertyResponseDto = oneRentalPropertyResponse();
        RentPropertyEntity rentalPropertyEntity = oneRentalPropertyEntity();

        int id = 1;

        when(rentalPropertyDtoMapper.mapToEntity(rentalPropertyRequestDto)).thenReturn(rentalPropertyEntity);
        when(rentalPropertyRepository.save(rentalPropertyEntity)).thenReturn(rentalPropertyEntity);
        when(rentalPropertyDtoMapper.mapToDto(rentalPropertyEntity)).thenReturn(rentalPropertyResponseDto);

        mockMvc.perform(put("/rental-properties/{id}", id)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(rentalPropertyRequestDto)))
                .andExpect(status().isOk());

        verify(rentalPropertyDtoMapper).mapToEntity(rentalPropertyRequestDto);
        verify(rentalPropertyRepository).save(rentalPropertyEntity);
        verifyNoMoreInteractions(rentalPropertyRepository, rentalPropertyDtoMapper);
    }

    @Test
    void givenInvalidBody_shouldNotUpdateRentalProperty() throws Exception {
        RentPropertyRequestDto invalidRequest = oneRentalPropertyRequestWithInvalidValue();

        int id = 1;

        JSONObject expectedJsonResponse = new JSONObject();
        expectedJsonResponse.put("message", "L'un des champs est manquant ou incorrect");

        mockMvc.perform(put("/rental-properties/{id}", id)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(expectedJsonResponse.toString()));

        verifyNoInteractions(rentalPropertyDtoMapper, rentalPropertyRepository);
    }


    @Test
    void givenInvalidJson_shouldNotUpdateRentalProperty() throws Exception {
        String invalidJson = "{\"id\":1,\"name\":\"\",\"address\":\"\",\"}";

        int id = 1;

        JSONObject expectedJsonResponse = new JSONObject();
        expectedJsonResponse.put("message", "La requête est mal formée ou un des champs est invalide");
        
        mockMvc.perform(put("/rental-properties/{id}", id)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(expectedJsonResponse.toString()));

        verifyNoInteractions(rentalPropertyDtoMapper, rentalPropertyRepository);
    }

}
