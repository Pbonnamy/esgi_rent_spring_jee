package fr.esgi.rent.samples;

import fr.esgi.rent.domain.RentalCarEntity;

import java.util.List;

public class RentalCarEntitySample {

    public static List<RentalCarEntity> rentalCarEntitiesSample() {
        return List.of(oneRentalCarEntitySample());
    }

    public static RentalCarEntity oneRentalCarEntitySample() {
        return new RentalCarEntity(
                1,
                "BMW",
                "Serie 1",
                790.9,
                1550.9,
                5,
                4,
                true
        );
    }
}
