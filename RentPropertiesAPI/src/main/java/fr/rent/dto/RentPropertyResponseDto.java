package fr.rent.dto;

import lombok.Builder;

@Builder
public record RentPropertyResponseDto(String description,
                                      String address,
                                      String town,
                                      String propertyType,
                                      double rentAmount,
                                      double securityDepositAmount,
                                      double area) {
}
