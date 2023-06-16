package fr.esgi.rent.service;

import fr.esgi.rent.domain.RentalCarEntity;
import fr.esgi.rent.repository.RentalCarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RentalCarService {

    private final RentalCarRepository rentalCarRepository;

    public void updateRentalCar(RentalCarEntity rentalCarEntity, Integer id) {
        rentalCarEntity.setId(id);
        rentalCarRepository.save(rentalCarEntity);
    }

    public void updateRentalCarPartially(RentalCarEntity rentalCarEntity, Double rentAmount) {
        rentalCarEntity.setRentAmount(rentAmount);
        rentalCarRepository.save(rentalCarEntity);
    }
}
