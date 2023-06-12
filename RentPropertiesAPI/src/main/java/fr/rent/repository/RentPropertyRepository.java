package fr.rent.repository;

import fr.rent.domain.entity.RentPropertyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RentPropertyRepository extends JpaRepository<RentPropertyEntity, Integer> {

}
