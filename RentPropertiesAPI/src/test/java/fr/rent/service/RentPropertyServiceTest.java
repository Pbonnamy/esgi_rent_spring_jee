package fr.rent.service;

import fr.rent.domain.entity.RentPropertyEntity;
import fr.rent.repository.RentPropertyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static fr.rent.samples.RentPropertyEntitySample.oneRentalPropertyEntity;
import static fr.rent.samples.RentPropertyEntitySample.rentalPropertyEntities;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RentPropertyServiceTest {


    @InjectMocks
    private RentPropertyService rentPropertyService;

    @Mock
    private RentPropertyRepository rentPropertyRepository;


    @Test
    void shouldFindAll() {

        List<RentPropertyEntity> rentalPropertyEntities = rentalPropertyEntities();

        when(rentPropertyRepository.findAll()).thenReturn(rentalPropertyEntities);

        List<RentPropertyEntity> rentProperties = rentPropertyService.findAll();

        assertThat(rentProperties).isEqualTo(rentalPropertyEntities);

        verify(rentPropertyRepository).findAll();
        verifyNoMoreInteractions(rentPropertyRepository);

    }

    @Test
    void shouldFindById() {

        RentPropertyEntity rentalPropertyEntity = oneRentalPropertyEntity();

        int id = 1;

        when(rentPropertyRepository.findById(id)).thenReturn(Optional.of(rentalPropertyEntity));

        Optional<RentPropertyEntity> rentProperty = rentPropertyService.findById(id);

        assertThat(rentProperty).isEqualTo(Optional.of(rentalPropertyEntity));

        verify(rentPropertyRepository).findById(id);
        verifyNoMoreInteractions(rentPropertyRepository);

    }

    @Test
    void shouldSave() {

        RentPropertyEntity rentalPropertyEntity = oneRentalPropertyEntity();

        when(rentPropertyRepository.save(rentalPropertyEntity)).thenReturn(rentalPropertyEntity);

        RentPropertyEntity rentProperty = rentPropertyService.save(rentalPropertyEntity);

        assertThat(rentProperty).isEqualTo(rentalPropertyEntity);

        verify(rentPropertyRepository).save(rentalPropertyEntity);
        verifyNoMoreInteractions(rentPropertyRepository);

    }

    @Test
    void shouldDeleteById() {

        int id = 1;

        doNothing().when(rentPropertyRepository).deleteById(id);

        rentPropertyService.deleteById(id);

        verify(rentPropertyRepository).deleteById(id);
        verifyNoMoreInteractions(rentPropertyRepository);

    }

    @Test
    void shouldUpdatePartiallyProperty() {
        RentPropertyEntity rentalCarEntity = oneRentalPropertyEntity();
        Double rentAmount = 100.0;

        when(rentPropertyRepository.save(rentalCarEntity)).thenReturn(rentalCarEntity);

        rentPropertyService.updatePartiallyProperty(rentalCarEntity, rentAmount);

        verify(rentPropertyRepository).save(rentalCarEntity);



    }

    @Test
    void shouldUpdateProperty() {

        RentPropertyEntity rentalPropertyEntity = oneRentalPropertyEntity();
        int id = 1;

        when(rentPropertyRepository.save(rentalPropertyEntity)).thenReturn(rentalPropertyEntity);

        rentPropertyService.update(rentalPropertyEntity, id);

        verify(rentPropertyRepository).save(rentalPropertyEntity);

    }


}
