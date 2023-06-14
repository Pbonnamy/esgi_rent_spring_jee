package fr.rent.application;

import fr.rent.domain.entity.RentPropertyEntity;
import fr.rent.dto.RentPropertyRequestDto;
import fr.rent.dto.RentPropertyResponseDto;
import fr.rent.dto.SimpleRequestDto;
import fr.rent.exception.RentPropertyNotFoundException;
import fr.rent.mapper.RentPropertyDtoMapper;
import fr.rent.repository.RentPropertyRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRentalProperty(@PathVariable int id, @Valid @RequestBody RentPropertyRequestDto rentalPropertyRequestDto) {

        RentPropertyEntity updatedEntity = rentalPropertyDtoMapper.mapToEntity(rentalPropertyRequestDto);

        updatedEntity.setId(id);

        rentPropertyRepository.save(updatedEntity);

        return ResponseEntity.ok().build();

    }

    @PatchMapping("/rental-properties/{id}")
    public ResponseEntity<Void> updateRentalPropertyPartially(@PathVariable int id, @Valid @RequestBody SimpleRequestDto simpleRequestDto) {
        RentPropertyEntity rentalPropertyEntity = rentPropertyRepository.findById(id)
                .orElseThrow(() -> new RentPropertyNotFoundException(id));

        //RentPropertyEntity updatedEntity = rentalPropertyDtoMapper.partialUpdateEntityFromDto(rentalPropertyEntity, rentalPropertyRequestDto);

        rentalPropertyEntity.setRentAmount(simpleRequestDto.rentAmount());

        rentPropertyRepository.save(rentalPropertyEntity);

        return ResponseEntity.ok().build();

    }


}
