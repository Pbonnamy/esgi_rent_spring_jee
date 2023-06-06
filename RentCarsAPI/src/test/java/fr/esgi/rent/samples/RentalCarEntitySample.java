package fr.esgi.rent.samples;

import fr.esgi.rent.domain.RentalCarEntity;

import java.util.List;

public class RentalCarEntitySample {

    public static List<RentalCarEntity> rentalCarEntities() {
        return List.of(oneRentalCarEntity());
    }

    public static RentalCarEntity oneRentalCarEntity() {
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
