package fr.esgi.rent.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "rental_car")
public class RentalCarEntity {

    public RentalCarEntity(String brand, String model, Double rentAmount, Double securityDepositAmount, Integer numberOfSeats, Integer numberOfDoors, Boolean hasAirConditioning) {
        this.brand = brand;
        this.model = model;
        this.rentAmount = rentAmount;
        this.securityDepositAmount = securityDepositAmount;
        this.numberOfSeats = numberOfSeats;
        this.numberOfDoors = numberOfDoors;
        this.hasAirConditioning = hasAirConditioning;
    }

    @Id
    @GeneratedValue()
    @Column(name = "id")
    private Integer id;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Column(name = "rent_amount")
    private Double rentAmount;

    @Column(name = "security_deposit_amount")
    private Double securityDepositAmount;

    @Column(name = "number_of_seats")
    private Integer numberOfSeats;

    @Column(name = "number_of_doors")
    private Integer numberOfDoors;

    @Column(name = "has_air_conditioning")
    private Boolean hasAirConditioning;
}
