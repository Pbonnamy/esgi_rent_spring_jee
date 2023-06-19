package fr.rent.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.rent.domain.entity.RentPropertyEntity;
import fr.rent.dto.RentPropertyRequestDto;
import fr.rent.dto.RentPropertyResponseDto;
import fr.rent.dto.SimpleRequestDto;
import fr.rent.mapper.RentPropertyDtoMapper;
import fr.rent.repository.RentPropertyRepository;
import fr.rent.service.RentPropertyService;
import io.github.cdimascio.dotenv.Dotenv;
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
    private RentPropertyService rentalPropertyService;

    @MockBean
    private RentPropertyDtoMapper rentalPropertyDtoMapper;

    private final ObjectMapper objectMapper = new ObjectMapper();

    Dotenv dotenv = Dotenv.configure()
            .directory("../")
            .filename(".env")
            .load();

    @Test
    void shouldReturnUnauthorizedWithoutToken() throws Exception {
        mockMvc.perform(get("/rental-properties"))
                .andExpect(status().isUnauthorized());
        verifyNoInteractions(rentalPropertyService, rentalPropertyDtoMapper);
    }

    @Test
    void shouldGetRentalProperties() throws Exception {
        List<RentPropertyEntity> rentalPropertyEntities = rentalPropertyEntities();
        List<RentPropertyResponseDto> rentalPropertyResponseList = rentalPropertyResponseList();

        when(rentalPropertyService.findAll()).thenReturn(rentalPropertyEntities);
        when(rentalPropertyDtoMapper.mapToDtoList(rentalPropertyEntities)).thenReturn(rentalPropertyResponseList);

        mockMvc.perform(get("/rental-properties").header("Authorization", "Bearer " + dotenv.get("AUTH_TOKEN")))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(rentalPropertyResponseList)));

        verify(rentalPropertyService).findAll();
        verify(rentalPropertyDtoMapper).mapToDtoList(rentalPropertyEntities);
        verifyNoMoreInteractions(rentalPropertyRepository, rentalPropertyDtoMapper);
    }

    @Test
    void shouldGetRentalPropertyById() throws Exception {
        RentPropertyEntity rentalPropertyEntity = oneRentalPropertyEntity();
        RentPropertyResponseDto rentalPropertyResponseDto = oneRentalPropertyResponse();

        int id = 1;

        when(rentalPropertyService.findById(id)).thenReturn(Optional.of(rentalPropertyEntity));
        when(rentalPropertyDtoMapper.mapToDto(rentalPropertyEntity)).thenReturn(rentalPropertyResponseDto);

        mockMvc.perform(get("/rental-properties/{id}", id).header("Authorization", "Bearer " + dotenv.get("AUTH_TOKEN")))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(rentalPropertyResponseDto)));

        verify(rentalPropertyService).findById(id);
        verify(rentalPropertyDtoMapper).mapToDto(rentalPropertyEntity);
        verifyNoMoreInteractions(rentalPropertyService, rentalPropertyDtoMapper);
    }

    @Test
    void givenNoExistentRentalPropertyId_shouldThrowNotFoundRentalPropertyException() throws Exception {

        int id = 1;
        JSONObject expectedJsonResponse = new JSONObject();
        expectedJsonResponse.put("message", "Impossible to find property with id " + id);

        when(rentalPropertyService.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/rental-properties/{id}", 1).header("Authorization", "Bearer " + dotenv.get("AUTH_TOKEN")))
                .andExpect(status().isNotFound())
                .andExpect(content().json(expectedJsonResponse.toString()));

        verify(rentalPropertyService).findById(id);
        verifyNoInteractions(rentalPropertyDtoMapper);
        verifyNoMoreInteractions(rentalPropertyService);
    }

    @Test
    void shouldCreateRentalProperty() throws Exception {
        RentPropertyRequestDto rentalPropertyRequestDto = oneRentalPropertyRequest();
        RentPropertyResponseDto rentalPropertyResponseDto = oneRentalPropertyResponse();
        RentPropertyEntity rentalPropertyEntity = oneRentalPropertyEntity();

        when(rentalPropertyDtoMapper.mapToEntity(rentalPropertyRequestDto)).thenReturn(rentalPropertyEntity);
        when(rentalPropertyService.save(rentalPropertyEntity)).thenReturn(rentalPropertyEntity);
        when(rentalPropertyDtoMapper.mapToDto(rentalPropertyEntity)).thenReturn(rentalPropertyResponseDto);

        mockMvc.perform(post("/rental-properties")
                        .header("Authorization", "Bearer " + dotenv.get("AUTH_TOKEN"))
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(rentalPropertyRequestDto)))
                .andExpect(status().isCreated());

        verify(rentalPropertyDtoMapper).mapToEntity(rentalPropertyRequestDto);
        verify(rentalPropertyService).save(rentalPropertyEntity);
        verifyNoMoreInteractions(rentalPropertyDtoMapper, rentalPropertyService);
    }

    @Test
    void givenInvalidRequestBody_shouldReturn404HttpStatusCode() throws Exception {

        RentPropertyRequestDto invalidRequest = oneRentalPropertyRequestWithInvalidValue();
        JSONObject expectedJsonResponse = new JSONObject();
        expectedJsonResponse.put("message", "One of the field is missing or is incorrect");

        mockMvc.perform(post("/rental-properties")
                        .header("Authorization", "Bearer " + dotenv.get("AUTH_TOKEN"))
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(expectedJsonResponse.toString()));

        verifyNoInteractions(rentalPropertyDtoMapper, rentalPropertyService);
    }

    @Test
    void shouldUpdateRentalProperty() throws Exception {
        RentPropertyRequestDto rentalPropertyRequestDto = oneRentalPropertyRequest();
        RentPropertyResponseDto rentalPropertyResponseDto = oneRentalPropertyResponse();
        RentPropertyEntity rentalPropertyEntity = oneRentalPropertyEntity();

        int id = 1;

        when(rentalPropertyDtoMapper.mapToEntity(rentalPropertyRequestDto)).thenReturn(rentalPropertyEntity);
        doNothing().when(rentalPropertyService).update(rentalPropertyEntity, id);
        when(rentalPropertyDtoMapper.mapToDto(rentalPropertyEntity)).thenReturn(rentalPropertyResponseDto);

        mockMvc.perform(put("/rental-properties/{id}", id)
                        .header("Authorization", "Bearer " + dotenv.get("AUTH_TOKEN"))
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(rentalPropertyRequestDto)))
                .andExpect(status().isOk());

        verify(rentalPropertyDtoMapper).mapToEntity(rentalPropertyRequestDto);
        verify(rentalPropertyService).save(rentalPropertyEntity);
        verifyNoMoreInteractions(rentalPropertyService, rentalPropertyDtoMapper);
    }

    @Test
    void givenInvalidBody_shouldNotUpdateRentalProperty() throws Exception {
        RentPropertyRequestDto invalidRequest = oneRentalPropertyRequestWithInvalidValue();

        int id = 1;

        JSONObject expectedJsonResponse = new JSONObject();
        expectedJsonResponse.put("message", "One of the field is missing or is incorrect");

        mockMvc.perform(put("/rental-properties/{id}", id)
                        .header("Authorization", "Bearer " + dotenv.get("AUTH_TOKEN"))
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(expectedJsonResponse.toString()));

        verifyNoInteractions(rentalPropertyDtoMapper, rentalPropertyService);
    }


    @Test
    void givenInvalidJson_shouldNotUpdateRentalProperty() throws Exception {
        String invalidJson = "{\"id\":1,\"name\":\"\",\"address\":\"\",\"}";

        int id = 1;

        JSONObject expectedJsonResponse = new JSONObject();
        expectedJsonResponse.put("message", "Request is invalid or one of the fields is missing");
        
        mockMvc.perform(put("/rental-properties/{id}", id)
                        .header("Authorization", "Bearer " + dotenv.get("AUTH_TOKEN"))
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(expectedJsonResponse.toString()));

        verifyNoInteractions(rentalPropertyDtoMapper, rentalPropertyService);
    }

    @Test
    void shouldDeleteRentalProperty() throws Exception {
        int id = 1;

        mockMvc.perform(delete("/rental-properties/{id}", id)
                        .header("Authorization", "Bearer " + dotenv.get("AUTH_TOKEN")))
                .andExpect(status().isNoContent());

        verify(rentalPropertyService).deleteById(id);
        verifyNoMoreInteractions(rentalPropertyService);
    }


    @Test
    void shouldPartiallyUpdateRentalProperty() throws Exception {

        int id = 1;

        SimpleRequestDto simpleRequestDto = oneSimpleRequest();
        RentPropertyEntity rentalPropertyEntity = oneRentalPropertyEntity();

        when(rentalPropertyService.findById(id)).thenReturn(Optional.of(rentalPropertyEntity));
        doNothing().when(rentalPropertyService).updatePartiallyProperty(rentalPropertyEntity, simpleRequestDto.rentAmount());

        mockMvc.perform(patch("/rental-properties/{id}", id)
                        .header("Authorization", "Bearer " + dotenv.get("AUTH_TOKEN"))
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(simpleRequestDto)))
                .andExpect(status().isOk());

        verify(rentalPropertyService).findById(id);
        verify(rentalPropertyService).updatePartiallyProperty(rentalPropertyEntity, simpleRequestDto.rentAmount());
        verifyNoMoreInteractions(rentalPropertyService);
    }


    @Test
    void givenNoExistingRentalProperty_shouldNotPartiallyUpdateRentalProperty() throws Exception {

        SimpleRequestDto simpleRequestDto = oneSimpleRequest();

        int id = 1;

        JSONObject expectedJsonResponse = new JSONObject();
        expectedJsonResponse.put("message", "Impossible to find property with id " + id);

        when(rentalPropertyService.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(patch("/rental-properties/{id}", id)
                        .header("Authorization", "Bearer " + dotenv.get("AUTH_TOKEN"))
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(simpleRequestDto)))
                .andExpect(status().isNotFound())
                .andExpect(content().json(expectedJsonResponse.toString()));

        verify(rentalPropertyService).findById(id);
        verifyNoMoreInteractions(rentalPropertyService);
    }


}
