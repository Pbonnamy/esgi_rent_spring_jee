package fr.rent.service;


import fr.rent.domain.entity.RentPropertyEntity;
import fr.rent.repository.RentPropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RentPropertyService {


    private final RentPropertyRepository rentalPropertyRepository;

    public List<RentPropertyEntity> findAll() {
        return rentalPropertyRepository.findAll();
    }

    public Optional<RentPropertyEntity> findById(int id) {
        return rentalPropertyRepository.findById(id);
    }

    public RentPropertyEntity save(RentPropertyEntity rentalPropertyEntity) {
        return rentalPropertyRepository.save(rentalPropertyEntity);
    }

    public void deleteById(int id) {
        rentalPropertyRepository.deleteById(id);
    }


}
