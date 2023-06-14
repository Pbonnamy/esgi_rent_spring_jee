package fr.esgi.rent.dto.response;

import lombok.Builder;

@Builder
public record RentalCarResponseDto(
        String brand,
        String model,
        Double rentAmount,
        Double securityDepositAmount,
        Integer numberOfSeats,
        Integer numberOfDoors,
        Boolean hasAirConditioning
) {}

