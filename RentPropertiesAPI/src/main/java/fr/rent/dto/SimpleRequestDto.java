package fr.rent.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record SimpleRequestDto(
        @NotNull Double rentAmount

) {}
