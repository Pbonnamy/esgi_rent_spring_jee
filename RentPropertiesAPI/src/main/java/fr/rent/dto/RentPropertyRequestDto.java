package fr.rent.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;


@Builder
public record RentPropertyRequestDto(
        @NotNull String description,
        @NotNull String town,
        @NotNull String address,
        @NotNull String propertyType,
        @NotNull double rentAmount,
        @NotNull double securityDepositAmount,
        @NotNull double area,
        @NotNull int number_of_bedrooms,
        int floorNumber,
        int numberOfFloors,
        int constructionYear,
        String energyClassification,
        boolean hasElevator,
        boolean hasIntercom,
        boolean hasBalcony,
        boolean hasParkingSpace) {

}
