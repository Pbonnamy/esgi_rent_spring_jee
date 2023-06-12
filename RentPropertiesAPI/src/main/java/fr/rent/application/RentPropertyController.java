package fr.rent.application;

import fr.rent.domain.entity.RentPropertyEntity;
import fr.rent.dto.RentPropertyRequestDto;
import fr.rent.dto.RentPropertyResponseDto;
import fr.rent.exception.RentPropertyNotFoundException;
import fr.rent.mapper.RentPropertyDtoMapper;
import fr.rent.repository.RentPropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/rental-properties")
@RequiredArgsConstructor
public class RentPropertyController {

    private final RentPropertyRepository rentPropertyRepository;

    private final RentPropertyDtoMapper rentalPropertyDtoMapper;


    @GetMapping()
    public ResponseEntity<List<RentPropertyResponseDto>> getRentalProperties() {
        List<RentPropertyEntity> rentalProperties = rentPropertyRepository.findAll();

        return ResponseEntity.ok().body(rentalPropertyDtoMapper.mapToDtoList(rentalProperties));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentPropertyResponseDto> getRentalPropertyById(@PathVariable int id) {
        return rentPropertyRepository.findById(id)
                .map(rentalPropertyDtoMapper::mapToDto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RentPropertyNotFoundException(id));
    }


    @PostMapping()
    public ResponseEntity<Void> createRentalProperty(@Valid @RequestBody RentPropertyRequestDto rentPropertyRequestDto) {
        RentPropertyEntity rentalPropertyEntity = rentalPropertyDtoMapper.mapToEntity(rentPropertyRequestDto);

        rentPropertyRepository.save(rentalPropertyEntity);

        return ResponseEntity.status(HttpStatus.CREATED).build();
        
    }


}
