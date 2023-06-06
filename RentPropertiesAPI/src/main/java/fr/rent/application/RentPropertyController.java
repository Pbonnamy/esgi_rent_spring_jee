package fr.rent.application;

import fr.rent.domain.entity.RentPropertyEntity;
import fr.rent.dto.RentPropertyRequestDto;
import fr.rent.dto.RentPropertyResponseDto;
import fr.rent.exception.RentPropertyNotFoundException;
import fr.rent.mapper.RentPropertyDtoMapper;
import fr.rent.service.RentPropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rent-properties-api")
public class RentPropertyController {

    private final RentPropertyService rentPropertyService;
    private final RentPropertyDtoMapper rentalPropertyDtoMapper;


    @GetMapping("/rental-properties")
    public List<RentPropertyResponseDto> getRentalProperties() {
        List<RentPropertyEntity> rentalProperties = rentPropertyService.findAll();

        return rentalPropertyDtoMapper.mapToDtoList(rentalProperties);
    }

    @GetMapping("/rental-properties/{id}")
    public RentPropertyResponseDto getRentalPropertyById(@PathVariable int id) {
        return rentPropertyService.findById(id)
                .map(rentalPropertyDtoMapper::mapToDto)
                .orElseThrow(() -> new RentPropertyNotFoundException(id));
    }


    @PostMapping("/rental-properties")
    @ResponseStatus(CREATED)
    public void createRentalProperty(@Valid @RequestBody RentPropertyRequestDto rentalPropertyResponseDto) {
        RentPropertyEntity rentalPropertyEntity = rentalPropertyDtoMapper.mapToEntity(rentalPropertyResponseDto);

        rentPropertyService.save(rentalPropertyEntity);
        
    }


    @PutMapping("/rental-properties/{id}")
    public void updateRentalProperty(@PathVariable int id, @Valid @RequestBody RentPropertyRequestDto rentalPropertyRequestDto) {
        RentPropertyEntity rentalPropertyEntity = rentPropertyService.findById(id)
                .orElseThrow(() -> new RentPropertyNotFoundException(id));

        RentPropertyEntity updatedEntity = rentalPropertyDtoMapper.updateEntityFromDto(rentalPropertyEntity, rentalPropertyRequestDto);

        rentPropertyService.save(updatedEntity);

    }


}
