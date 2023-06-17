package fr.rent.application;

import fr.rent.domain.entity.RentPropertyEntity;
import fr.rent.dto.RentPropertyRequestDto;
import fr.rent.dto.RentPropertyResponseDto;
import fr.rent.dto.SimpleRequestDto;
import fr.rent.exception.RentPropertyNotFoundException;
import fr.rent.mapper.RentPropertyDtoMapper;
import fr.rent.service.RentPropertyService;
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

    private final RentPropertyService rentPropertyservice;

    private final RentPropertyDtoMapper rentalPropertyDtoMapper;


    @GetMapping()
    public ResponseEntity<List<RentPropertyResponseDto>> getRentalProperties() {
        List<RentPropertyEntity> rentalProperties = rentPropertyservice.findAll();

        return ResponseEntity.ok().body(rentalPropertyDtoMapper.mapToDtoList(rentalProperties));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentPropertyResponseDto> getRentalPropertyById(@PathVariable int id) {
        return rentPropertyservice.findById(id)
                .map(rentalPropertyDtoMapper::mapToDto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RentPropertyNotFoundException(id));
    }


    @PostMapping()
    public ResponseEntity<Void> createRentalProperty(@Valid @RequestBody RentPropertyRequestDto rentPropertyRequestDto) {
        RentPropertyEntity rentalPropertyEntity = rentalPropertyDtoMapper.mapToEntity(rentPropertyRequestDto);

        rentPropertyservice.save(rentalPropertyEntity);

        return ResponseEntity.status(HttpStatus.CREATED).build();
        
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRentalProperty(@PathVariable int id, @Valid @RequestBody RentPropertyRequestDto rentalPropertyRequestDto) {

        RentPropertyEntity updatedEntity = rentalPropertyDtoMapper.mapToEntity(rentalPropertyRequestDto);

        updatedEntity.setId(id);

        rentPropertyservice.save(updatedEntity);

        return ResponseEntity.ok().build();

    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateRentalPropertyPartially(@PathVariable int id, @Valid @RequestBody SimpleRequestDto simpleRequestDto) {
        RentPropertyEntity rentalPropertyEntity = rentPropertyservice.findById(id)
                .orElseThrow(() -> new RentPropertyNotFoundException(id));

        rentPropertyservice.updatePartiallyProperty(rentalPropertyEntity, simpleRequestDto.rentAmount());

        return ResponseEntity.ok().build();

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRentalProperty(@PathVariable int id) {

        rentPropertyservice.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }


}
