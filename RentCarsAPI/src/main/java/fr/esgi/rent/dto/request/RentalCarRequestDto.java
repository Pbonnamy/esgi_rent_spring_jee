package fr.esgi.rent.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record RentalCarRequestDto (
        @NotNull String brand,
        @NotNull String model,
        @NotNull Double rentAmount,
        @NotNull Double securityDepositAmount,
        Integer numberOfSeats,
        Integer numberOfDoors,
        Boolean hasAirConditioning
) {
}
