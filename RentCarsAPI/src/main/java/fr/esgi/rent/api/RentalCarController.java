package fr.esgi.rent.api;

import fr.esgi.rent.domain.RentalCarEntity;
import fr.esgi.rent.dto.request.RentalCarRequestDto;
import fr.esgi.rent.dto.response.RentalCarResponseDto;
import fr.esgi.rent.exception.NotFoundRentalCarException;
import fr.esgi.rent.repository.RentalCarRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import fr.esgi.rent.mapper.RentalCarDtoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rental-cars")
@RequiredArgsConstructor
public class RentalCarController {

    private final RentalCarRepository rentalCarRepository;
    private final RentalCarDtoMapper rentalCarDtoMapper;

    @GetMapping()
    public ResponseEntity<List<RentalCarResponseDto>> getRentalCars() {

        List<RentalCarEntity> rentalCarEntities = rentalCarRepository.findAll();

        return ResponseEntity.ok().body(rentalCarDtoMapper.toDtoList(rentalCarEntities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalCarResponseDto> getRentalCarById(@PathVariable Integer id) {

        return rentalCarRepository.findById(id)
                .map(rentalCarDtoMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NotFoundRentalCarException("Rental car with id " + id + " not found"));
    }

    @PostMapping()
    public ResponseEntity<Void> createRentalCar(@Valid @RequestBody RentalCarRequestDto rentalCarRequestDto) {
        RentalCarEntity rentalCarEntity = rentalCarDtoMapper.toEntity(rentalCarRequestDto);

        rentalCarRepository.save(rentalCarEntity);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
