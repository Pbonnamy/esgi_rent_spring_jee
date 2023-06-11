package fr.rent.samples;

import fr.rent.dto.RentPropertyRequestDto;
import fr.rent.dto.RentPropertyResponseDto;

import java.util.List;

public class RentPropertyDtoSample {

    public static List<RentPropertyResponseDto> rentalPropertyResponseList() {
        RentPropertyResponseDto rentalProperty = oneRentalPropertyResponse();

        return List.of(rentalProperty);
    }

    public static RentPropertyResponseDto oneRentalPropertyResponse() {
        return RentPropertyResponseDto.builder()
                .description("Appartement spacieux avec vue sur l'ESGI")
                .town("Paris")
                .address("77 Rue des roses")
                .propertyType("Appartement")
                .rentAmount(750.90)
                .securityDepositAmount(1200.90)
                .area(37.48)
                .build();
    }

    public static RentPropertyRequestDto oneRentalPropertyRequest() {
        return RentPropertyRequestDto.builder()
                .description("Appartement spacieux avec vue sur l'ESGI")
                .town("Paris")
                .address("77 Rue des roses")
                .propertyType("Appartement")
                .rentAmount(750.90)
                .securityDepositAmount(1200.90)
                .area(37.48)
                .number_of_bedrooms(2)
                .floorNumber(1)
                .numberOfFloors(3)
                .constructionYear(1990)
                .energyClassification("B")
                .hasElevator(false)
                .hasIntercom(false)
                .hasBalcony(true)
                .hasParkingSpace(false)
                .build();
    }

    public static RentPropertyRequestDto oneRentalPropertyRequestWithInvalidValue() {
        return RentPropertyRequestDto.builder()
                .description("null")
                .town("null")
                .address("null")
                .build();
    }

}
