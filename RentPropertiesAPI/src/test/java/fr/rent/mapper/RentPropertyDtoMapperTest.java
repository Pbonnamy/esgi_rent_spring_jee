package fr.rent.mapper;

import fr.rent.domain.entity.RentPropertyEntity;
import fr.rent.dto.RentPropertyRequestDto;
import fr.rent.dto.RentPropertyResponseDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static fr.rent.samples.RentPropertyDtoSample.oneRentalPropertyRequest;
import static fr.rent.samples.RentPropertyDtoSample.oneRentalPropertyResponse;
import static fr.rent.samples.RentPropertyEntitySample.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class RentPropertyDtoMapperTest {

    @Test
    void shouldMapToDto() {
        RentPropertyEntity rentalPropertyEntity = oneRentalPropertyEntity();
        RentPropertyResponseDto expectedRentalPropertyResponseDto = oneRentalPropertyResponse();

        RentPropertyDtoMapper rentalPropertyDtoMapper = new RentPropertyDtoMapper();

        RentPropertyResponseDto rentalPropertyResponseDto = rentalPropertyDtoMapper.mapToDto(rentalPropertyEntity);

        assertThat(rentalPropertyResponseDto).isEqualTo(expectedRentalPropertyResponseDto);
    }

    @Test
    void shouldMapToDtoList() {
        List<RentPropertyEntity> rentalPropertyEntities = rentalPropertyEntities();

        RentPropertyDtoMapper rentalPropertyDtoMapper = new RentPropertyDtoMapper();

        List<RentPropertyResponseDto> rentalPropertyResponseList = rentalPropertyDtoMapper.mapToDtoList(rentalPropertyEntities);

        assertThat(rentalPropertyResponseList).isNotNull()
                .hasSize(1)
                .extracting("description", "address", "town", "propertyType", "rentAmount", "securityDepositAmount", "area")
                .containsExactlyInAnyOrder(
                        tuple("Appartement spacieux avec vue sur l'ESGI",
                                "77 Rue des roses",
                                "Paris",
                                "Appartement",
                                750.90,
                                1200.90,
                                37.48)
                );
    }

    @Test
    void shouldMapToEntity() {
        RentPropertyRequestDto rentalPropertyRequestDto = oneRentalPropertyRequest();
        RentPropertyEntity expectedRentalPropertyEntity = oneRentalPropertyEntityWithoutId();

        RentPropertyDtoMapper rentalPropertyDtoMapper = new RentPropertyDtoMapper();

        RentPropertyEntity rentalPropertyEntity = rentalPropertyDtoMapper.mapToEntity(rentalPropertyRequestDto);

        assertThat(rentalPropertyEntity).usingRecursiveComparison().isEqualTo(expectedRentalPropertyEntity);
    }

}
