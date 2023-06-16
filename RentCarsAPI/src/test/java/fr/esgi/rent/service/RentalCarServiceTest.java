package fr.esgi.rent.service;

import fr.esgi.rent.domain.RentalCarEntity;
import fr.esgi.rent.repository.RentalCarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static fr.esgi.rent.samples.RentalCarEntitySample.oneRentalCarEntitySample;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RentalCarServiceTest {

    @InjectMocks
    private RentalCarService rentalCarService;

    @Mock
    private RentalCarRepository rentalCarRepository;

    @Test
    void shouldUpdateRentalCar() {
        RentalCarEntity rentalCarEntity = oneRentalCarEntitySample();
        Integer id = 1;

        when(rentalCarRepository.save(rentalCarEntity)).thenReturn(rentalCarEntity);

        rentalCarService.updateRentalCar(rentalCarEntity, id);

        verify(rentalCarRepository).save(rentalCarEntity);
    }

    @Test
    void shouldUpdateRentalCarPartially() {
        RentalCarEntity rentalCarEntity = oneRentalCarEntitySample();
        Double rentAmount = 100.0;

        when(rentalCarRepository.save(rentalCarEntity)).thenReturn(rentalCarEntity);

        rentalCarService.updateRentalCarPartially(rentalCarEntity, rentAmount);

        verify(rentalCarRepository).save(rentalCarEntity);
    }

}