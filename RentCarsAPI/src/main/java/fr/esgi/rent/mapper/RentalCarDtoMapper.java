package fr.esgi.rent.mapper;

import fr.esgi.rent.domain.RentalCarEntity;
import fr.esgi.rent.dto.response.RentalCarDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RentalCarDtoMapper {

    public RentalCarDto toDto(RentalCarEntity rentalCarEntity) {
        return RentalCarDto.builder()
                .brand(rentalCarEntity.getBrand())
                .model(rentalCarEntity.getModel())
                .rentAmount(rentalCarEntity.getRentAmount())
                .securityDepositAmount(rentalCarEntity.getSecurityDepositAmount())
                .numberOfSeats(rentalCarEntity.getNumberOfSeats())
                .numberOfDoors(rentalCarEntity.getNumberOfDoors())
                .hasAirConditioning(rentalCarEntity.getHasAirConditioning())
                .build();
    }

    public List<RentalCarDto> toDtoList(List<RentalCarEntity> rentalCarEntities) {
        return rentalCarEntities.stream()
                .map(this::toDto)
                .toList();
    }
}
