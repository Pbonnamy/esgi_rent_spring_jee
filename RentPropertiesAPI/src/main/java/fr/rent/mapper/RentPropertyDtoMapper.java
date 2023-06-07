package fr.rent.mapper;

import fr.rent.domain.entity.EnergyClassificationEntity;
import fr.rent.domain.entity.PropertyTypeEntity;
import fr.rent.domain.entity.RentPropertyEntity;
import fr.rent.dto.RentPropertyRequestDto;
import fr.rent.dto.RentPropertyResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RentPropertyDtoMapper {

    public List<RentPropertyResponseDto> mapToDtoList(List<RentPropertyEntity> rentalProperties) {
        return rentalProperties.stream()
                .map(this::mapToDto)
                .toList();
    }

    public RentPropertyResponseDto mapToDto(RentPropertyEntity rentalProperty) {
        return new RentPropertyResponseDto(
                rentalProperty.getDescription(),
                rentalProperty.getAddress(),
                rentalProperty.getTown(),
                rentalProperty.getPropertyType().getDesignation(),
                rentalProperty.getRentAmount(),
                rentalProperty.getSecurityDepositAmount(),
                rentalProperty.getArea());
    }

    public RentPropertyEntity mapToEntity(RentPropertyRequestDto rentalPropertyRequestDto) {
        return new RentPropertyEntity(
                rentalPropertyRequestDto.description(),
                rentalPropertyRequestDto.town(),
                rentalPropertyRequestDto.address(),
                new PropertyTypeEntity(rentalPropertyRequestDto.propertyType()),
                rentalPropertyRequestDto.rentAmount(),
                rentalPropertyRequestDto.securityDepositAmount(),
                rentalPropertyRequestDto.area(),
                rentalPropertyRequestDto.number_of_bedrooms(),
                rentalPropertyRequestDto.floorNumber(),
                rentalPropertyRequestDto.numberOfFloors(),
                rentalPropertyRequestDto.constructionYear(),
                new EnergyClassificationEntity(rentalPropertyRequestDto.energyClassification()),
                rentalPropertyRequestDto.hasElevator(),
                rentalPropertyRequestDto.hasIntercom(),
                rentalPropertyRequestDto.hasBalcony(),
                rentalPropertyRequestDto.hasParkingSpace());
    }

    public RentPropertyEntity updateEntityFromDto(RentPropertyEntity rentPropertyEntity, RentPropertyRequestDto rentPropertyRequestDto) {
        rentPropertyEntity.setDescription(rentPropertyRequestDto.description());
        rentPropertyEntity.setTown(rentPropertyRequestDto.town());
        rentPropertyEntity.setAddress(rentPropertyRequestDto.address());
        rentPropertyEntity.setPropertyType(new PropertyTypeEntity(rentPropertyRequestDto.propertyType()));
        rentPropertyEntity.setRentAmount(rentPropertyRequestDto.rentAmount());
        rentPropertyEntity.setSecurityDepositAmount(rentPropertyRequestDto.securityDepositAmount());
        rentPropertyEntity.setArea(rentPropertyRequestDto.area());
        rentPropertyEntity.setNumberOfBedrooms(rentPropertyRequestDto.number_of_bedrooms());
        rentPropertyEntity.setFloorNumber(rentPropertyRequestDto.floorNumber());
        rentPropertyEntity.setNumberOfFloors(rentPropertyRequestDto.numberOfFloors());
        rentPropertyEntity.setConstructionYear(rentPropertyRequestDto.constructionYear());
        rentPropertyEntity.setEnergyClassification(new EnergyClassificationEntity(rentPropertyRequestDto.energyClassification()));
        rentPropertyEntity.setHasElevator(rentPropertyRequestDto.hasElevator());
        rentPropertyEntity.setHasIntercom(rentPropertyRequestDto.hasIntercom());
        rentPropertyEntity.setHasBalcony(rentPropertyRequestDto.hasBalcony());
        rentPropertyEntity.setHasParkingSpace(rentPropertyRequestDto.hasParkingSpace());
        return rentPropertyEntity;
    }

    public RentPropertyEntity partialUpdateEntityFromDto(RentPropertyEntity rentPropertyEntity, RentPropertyRequestDto rentPropertyRequestDto) {
        rentPropertyEntity.setDescription(rentPropertyRequestDto.description());
        rentPropertyEntity.setTown(rentPropertyRequestDto.town());
        rentPropertyEntity.setAddress(rentPropertyRequestDto.address());
        rentPropertyEntity.setPropertyType(new PropertyTypeEntity(rentPropertyRequestDto.propertyType()));

        if (rentPropertyRequestDto.rentAmount() != 0) {
            rentPropertyEntity.setRentAmount(rentPropertyRequestDto.rentAmount());
        }
        if (rentPropertyRequestDto.securityDepositAmount() != 0) {
            rentPropertyEntity.setSecurityDepositAmount(rentPropertyRequestDto.securityDepositAmount());
        }
        if (rentPropertyRequestDto.area() != 0) {
            rentPropertyEntity.setArea(rentPropertyRequestDto.area());
        }
        if (rentPropertyRequestDto.number_of_bedrooms() != 0) {
            rentPropertyEntity.setNumberOfBedrooms(rentPropertyRequestDto.number_of_bedrooms());
        }
        if (rentPropertyRequestDto.floorNumber() != 0) {
            rentPropertyEntity.setFloorNumber(rentPropertyRequestDto.floorNumber());
        }
        if (rentPropertyRequestDto.numberOfFloors() != 0) {
            rentPropertyEntity.setNumberOfFloors(rentPropertyRequestDto.numberOfFloors());
        }
        if (rentPropertyRequestDto.constructionYear() != 0) {
            rentPropertyEntity.setConstructionYear(rentPropertyRequestDto.constructionYear());
        }
        if (rentPropertyRequestDto.energyClassification() != null) {
            rentPropertyEntity.setEnergyClassification(new EnergyClassificationEntity(rentPropertyRequestDto.energyClassification()));
        }
        if (rentPropertyRequestDto.hasElevator()) {
            rentPropertyEntity.setHasElevator(rentPropertyRequestDto.hasElevator());
        }
        if (rentPropertyRequestDto.hasIntercom()) {
            rentPropertyEntity.setHasIntercom(rentPropertyRequestDto.hasIntercom());
        }
        if (rentPropertyRequestDto.hasBalcony()) {
            rentPropertyEntity.setHasBalcony(rentPropertyRequestDto.hasBalcony());
        }
        if (rentPropertyRequestDto.hasParkingSpace()) {
            rentPropertyEntity.setHasParkingSpace(rentPropertyRequestDto.hasParkingSpace());
        }

        return rentPropertyEntity;
    }
}
