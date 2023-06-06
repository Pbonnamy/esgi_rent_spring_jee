package fr.esgi.rent.service;

import fr.esgi.rent.domain.RentalCarEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import fr.esgi.rent.repository.RentalCarRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RentalCarService {

    private final RentalCarRepository rentalCarRepository;

    public List<RentalCarEntity> findAll() {
        return rentalCarRepository.findAll();
    }

    public Optional<RentalCarEntity> findById(Integer id) {
        return rentalCarRepository.findById(id);
    }
}
