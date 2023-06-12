package fr.rent.samples;

import fr.rent.domain.entity.EnergyClassificationEntity;
import fr.rent.domain.entity.PropertyTypeEntity;
import fr.rent.domain.entity.RentPropertyEntity;

import java.util.List;

public class RentPropertyEntitySample {

    public static List<RentPropertyEntity> rentalPropertyEntities() {
        RentPropertyEntity rentalProperty = oneRentalPropertyEntity();

        return List.of(rentalProperty);
    }

    public static RentPropertyEntity oneRentalPropertyEntity() {
        return new RentPropertyEntity(
                1,
                "Appartement spacieux avec vue sur l'ESGI",
                "Paris",
                "77 Rue des roses",
                new PropertyTypeEntity(1, "Appartement"),
                750.90,
                1200.90,
                37.48,
                2,
                1,
                3,
                1990,
                new EnergyClassificationEntity(2, "B"),
                false,
                false,
                true,
                false);
    }

}
