package fr.esgi.rent.api;

import fr.esgi.rent.domain.RentalCarEntity;
import fr.esgi.rent.dto.response.RentalCarResponseDto;
import lombok.RequiredArgsConstructor;
import fr.esgi.rent.mapper.RentalCarDtoMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import fr.esgi.rent.service.RentalCarService;

import java.util.List;

@RestController
@RequestMapping("/rental-cars")
@RequiredArgsConstructor
public class RentalCarController {

    private final RentalCarService rentalCarService;
    private final RentalCarDtoMapper rentalCarDtoMapper;

    @GetMapping()
    public ResponseEntity<List<RentalCarResponseDto>> getRentalCars() {

        List<RentalCarEntity> rentalCarEntities = rentalCarService.findAll();

        return ResponseEntity.ok().body(rentalCarDtoMapper.toDtoList(rentalCarEntities));
    }
}
