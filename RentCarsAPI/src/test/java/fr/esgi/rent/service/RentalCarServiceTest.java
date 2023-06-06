package fr.esgi.rent.service;

import fr.esgi.rent.domain.RentalCarEntity;
import fr.esgi.rent.repository.RentalCarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static fr.esgi.rent.samples.RentalCarEntitySample.rentalCarEntities;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RentalCarServiceTest {

    @InjectMocks
    private RentalCarService rentalCarService;

    @Mock
    private RentalCarRepository rentalCarRepository;

    @Test
    void shouldFindAllRentalCars() {
        List<RentalCarEntity> expectedRentalCarEntities = rentalCarEntities();

        when(rentalCarRepository.findAll()).thenReturn(expectedRentalCarEntities);

        List<RentalCarEntity> actualRentalCarEntities = rentalCarService.findAll();

        assertThat(actualRentalCarEntities)
                .isNotNull()
                .hasSize(1)
                .containsExactlyElementsOf(expectedRentalCarEntities);

        verify(rentalCarRepository).findAll();
        verifyNoMoreInteractions(rentalCarRepository);
    }

}