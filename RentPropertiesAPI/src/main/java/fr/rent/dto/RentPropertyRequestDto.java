package fr.rent.dto;

import lombok.Builder;
import org.antlr.v4.runtime.misc.NotNull;

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
