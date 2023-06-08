package fr.esgi.rent.mapper;

import fr.esgi.rent.domain.RentalCarEntity;
import fr.esgi.rent.dto.request.RentalCarRequestDto;
import fr.esgi.rent.dto.response.RentalCarResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RentalCarDtoMapper {

    public RentalCarResponseDto toDto(RentalCarEntity rentalCarEntity) {
        return RentalCarResponseDto.builder()
                .brand(rentalCarEntity.getBrand())
                .model(rentalCarEntity.getModel())
                .rentAmount(rentalCarEntity.getRentAmount())
                .securityDepositAmount(rentalCarEntity.getSecurityDepositAmount())
                .numberOfSeats(rentalCarEntity.getNumberOfSeats())
                .numberOfDoors(rentalCarEntity.getNumberOfDoors())
                .hasAirConditioning(rentalCarEntity.getHasAirConditioning())
                .build();
    }

    public List<RentalCarResponseDto> toDtoList(List<RentalCarEntity> rentalCarEntities) {
        return rentalCarEntities.stream()
                .map(this::toDto)
                .toList();
    }

    public RentalCarEntity toEntity(RentalCarRequestDto rentalCarRequestDto) {
        return new RentalCarEntity(
                rentalCarRequestDto.brand(),
                rentalCarRequestDto.model(),
                rentalCarRequestDto.rentAmount(),
                rentalCarRequestDto.securityDepositAmount(),
                rentalCarRequestDto.numberOfSeats(),
                rentalCarRequestDto.numberOfDoors(),
                rentalCarRequestDto.hasAirConditioning()
        );
    }
}
