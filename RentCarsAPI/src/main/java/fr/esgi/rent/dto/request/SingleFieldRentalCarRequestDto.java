package fr.esgi.rent.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record SingleFieldRentalCarRequestDto(
        @NotNull Double rentAmount
) {
}
