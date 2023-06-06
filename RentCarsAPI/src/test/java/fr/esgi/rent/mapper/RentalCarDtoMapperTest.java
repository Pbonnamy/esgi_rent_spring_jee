package fr.esgi.rent.mapper;

import fr.esgi.rent.domain.RentalCarEntity;
import fr.esgi.rent.dto.response.RentalCarResponseDto;
import fr.esgi.rent.samples.RentalCarEntitySample;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static fr.esgi.rent.samples.RentalCarDtoSample.oneRentalCarResponseDto;
import static fr.esgi.rent.samples.RentalCarDtoSample.rentalCarResponseDtos;
import static fr.esgi.rent.samples.RentalCarEntitySample.oneRentalCarEntity;
import static fr.esgi.rent.samples.RentalCarEntitySample.rentalCarEntities;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RentalCarDtoMapperTest {

    private final RentalCarDtoMapper rentalCarDtoMapper = new RentalCarDtoMapper();

    @Test
    void shouldMapToDto() {
        RentalCarEntity rentalCarEntity = oneRentalCarEntity();
        RentalCarResponseDto expectedRentalCarResponseDto = oneRentalCarResponseDto();

        RentalCarResponseDto actualRentalCarResponseDto = rentalCarDtoMapper.toDto(rentalCarEntity);

        assertEquals(expectedRentalCarResponseDto, actualRentalCarResponseDto);
    }

    @Test
    void shouldMapToDtoList() {
        List<RentalCarEntity> rentalCarEntities = rentalCarEntities();
        List<RentalCarResponseDto> expectedRentalCarResponseDtos = rentalCarResponseDtos();

        List<RentalCarResponseDto> actualRentalCarResponseDtos = rentalCarDtoMapper.toDtoList(rentalCarEntities);

        assertThat(actualRentalCarResponseDtos)
                .isNotNull()
                .hasSize(1)
                .containsExactlyElementsOf(expectedRentalCarResponseDtos);
    }

}