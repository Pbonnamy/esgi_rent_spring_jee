package fr.esgi.rent.samples;

import fr.esgi.rent.dto.response.RentalCarResponseDto;

import java.util.List;

public class RentalCarDtoSample {

    public static List<RentalCarResponseDto> rentalCarResponseDtos() {
        return List.of(oneRentalCarResponseDto());
    }

    public static RentalCarResponseDto oneRentalCarResponseDto() {
        return RentalCarResponseDto.builder()
                .brand("BMW")
                .model("Serie 1")
                .rentAmount(790.9)
                .securityDepositAmount(1550.9)
                .numberOfSeats(5)
                .numberOfDoors(4)
                .hasAirConditioning(true)
                .build();
    }
}
