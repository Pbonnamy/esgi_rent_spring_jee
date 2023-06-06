package fr.rent.application;

import fr.rent.domain.entity.RentPropertyEntity;
import fr.rent.dto.RentPropertyRequestDto;
import fr.rent.dto.RentPropertyResponseDto;
import fr.rent.exception.RentPropertyNotFoundException;
import fr.rent.mapper.RentPropertyDtoMapper;
import fr.rent.repository.RentPropertyRepository;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

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


    @PostMapping("/rental-properties")
    @ResponseStatus(CREATED)
    public RentPropertyResponseDto createRentalProperty(@Valid @RequestBody RentPropertyRequestDto rentalPropertyResponseDto) {
        RentPropertyEntity rentalPropertyEntity = rentalPropertyDtoMapper.mapToEntity(rentalPropertyResponseDto);

        RentPropertyEntity savedRentalProperty = rentalPropertyRepository.save(rentalPropertyEntity);

        return rentalPropertyDtoMapper.mapToDto(savedRentalProperty);
    }


    @PutMapping("/rental-properties/{id}")
    public RentPropertyResponseDto updateRentalProperty(@PathVariable int id, @Valid @RequestBody RentPropertyRequestDto rentalPropertyResponseDto) {
        RentPropertyEntity rentalPropertyEntity = rentalPropertyRepository.findById(id)
                .orElseThrow(() -> new RentPropertyNotFoundException(id));

        //rentalPropertyDtoMapper.updateEntityFromDto(rentalPropertyResponseDto, rentalPropertyEntity);

        RentPropertyEntity updatedRentalProperty = rentalPropertyRepository.save(rentalPropertyEntity);

        return rentalPropertyDtoMapper.mapToDto(updatedRentalProperty);
    }


}
