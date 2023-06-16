package fr.esgi.rent.samples;

import fr.esgi.rent.dto.request.RentalCarRequestDto;
import fr.esgi.rent.dto.request.SingleFieldRentalCarRequestDto;
import fr.esgi.rent.dto.response.RentalCarResponseDto;

import java.util.List;

public class RentalCarDtoSample {

    public static List<RentalCarResponseDto> rentalCarResponseDtosSample() {
        return List.of(oneRentalCarResponseDtoSample());
    }

    public static RentalCarResponseDto oneRentalCarResponseDtoSample() {
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

    public static RentalCarRequestDto oneRentalCarRequestDtoSample() {
        return RentalCarRequestDto.builder()
                .brand("BMW")
                .model("Serie 1")
                .rentAmount(790.9)
                .securityDepositAmount(1550.9)
                .numberOfSeats(5)
                .numberOfDoors(4)
                .hasAirConditioning(true)
                .build();
    }

    public static SingleFieldRentalCarRequestDto oneSingleFieldRentalCarRequestDtoSample() {
        return SingleFieldRentalCarRequestDto.builder()
                .rentAmount(790.9)
                .build();
    }

    public static RentalCarRequestDto oneInvalidRentalCarRequestDtoSample() {
        return RentalCarRequestDto.builder()
                .brand("BMW")
                .model("Serie 1")
                .build();
    }


    public static SingleFieldRentalCarRequestDto oneInvalidSingleFieldRentalCarRequestDtoSample() {
        return SingleFieldRentalCarRequestDto.builder().build();
    }
}
