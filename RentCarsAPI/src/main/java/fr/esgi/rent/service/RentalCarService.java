package fr.esgi.rent.service;

import fr.esgi.rent.domain.RentalCarEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import fr.esgi.rent.repository.RentalCarRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalCarService {

    private final RentalCarRepository rentalCarRepository;

    public List<RentalCarEntity> getAll() {
        return rentalCarRepository.findAll();
    }
}
