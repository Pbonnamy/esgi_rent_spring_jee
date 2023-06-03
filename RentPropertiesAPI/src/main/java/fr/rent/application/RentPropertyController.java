package fr.rent.application;

import fr.rent.domain.entity.RentPropertyEntity;
import fr.rent.dto.RentPropertyResponseDto;
import fr.rent.exception.RentPropertyNotFoundException;
import fr.rent.mapper.RentPropertyDtoMapper;
import fr.rent.repository.RentPropertyRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rent-properties-api")
public class RentPropertyController {

    private final RentPropertyRepository rentalPropertyRepository;
    private final RentPropertyDtoMapper rentalPropertyDtoMapper;

    public RentPropertyController(RentPropertyRepository rentalPropertyRepository,
                                  RentPropertyDtoMapper rentalPropertyDtoMapper) {
        this.rentalPropertyRepository = rentalPropertyRepository;
        this.rentalPropertyDtoMapper = rentalPropertyDtoMapper;
    }


    @GetMapping("/rental-properties")
    public List<RentPropertyResponseDto> getRentalProperties() {
        List<RentPropertyEntity> rentalProperties = rentalPropertyRepository.findAll();

        return rentalPropertyDtoMapper.mapToDtoList(rentalProperties);
    }

    @GetMapping("/rental-properties/{id}")
    public RentPropertyResponseDto getRentalPropertyById(@PathVariable int id) {
        return rentalPropertyRepository.findById(id)
                .map(rentalPropertyDtoMapper::mapToDto)
                .orElseThrow(() -> new RentPropertyNotFoundException(id));
    }



}
