package fr.esgi.rent.mapper;

import fr.esgi.rent.domain.RentalCarEntity;
import fr.esgi.rent.dto.request.RentalCarRequestDto;
import fr.esgi.rent.dto.response.RentalCarResponseDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static fr.esgi.rent.samples.RentalCarDtoSample.*;
import static fr.esgi.rent.samples.RentalCarEntitySample.oneRentalCarEntitySample;
import static fr.esgi.rent.samples.RentalCarEntitySample.rentalCarEntitiesSample;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RentalCarDtoMapperTest {

    private final RentalCarDtoMapper rentalCarDtoMapper = new RentalCarDtoMapper();

    @Test
    void shouldMapToDto() {
        RentalCarEntity rentalCarEntity = oneRentalCarEntitySample();
        RentalCarResponseDto expectedRentalCarResponseDto = oneRentalCarResponseDtoSample();

        RentalCarResponseDto actualRentalCarResponseDto = rentalCarDtoMapper.toDto(rentalCarEntity);

        assertEquals(expectedRentalCarResponseDto, actualRentalCarResponseDto);
    }

    @Test
    void shouldMapToDtoList() {
        List<RentalCarEntity> rentalCarEntities = rentalCarEntitiesSample();
        List<RentalCarResponseDto> expectedRentalCarResponseDtos = rentalCarResponseDtosSample();

        List<RentalCarResponseDto> actualRentalCarResponseDtos = rentalCarDtoMapper.toDtoList(rentalCarEntities);

        assertThat(actualRentalCarResponseDtos)
                .isNotNull()
                .hasSize(1)
                .containsExactlyElementsOf(expectedRentalCarResponseDtos);
    }

    @Test
    void shouldMapToEntity() {
        RentalCarEntity expectedRentalCarEntity = oneRentalCarEntitySample();
        RentalCarRequestDto rentalCarRequestDto = oneRentalCarRequestDtoSample();

        RentalCarEntity actualRentalCarEntity = rentalCarDtoMapper.toEntity(rentalCarRequestDto);

        assertThat(actualRentalCarEntity)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expectedRentalCarEntity);
    }

}