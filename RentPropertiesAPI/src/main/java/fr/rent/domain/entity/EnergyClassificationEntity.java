package fr.rent.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "energy_classification")
public class EnergyClassificationEntity {
    public EnergyClassificationEntity() {

    }

    public EnergyClassificationEntity(String designation) {
        this.designation = designation;
    }

    public EnergyClassificationEntity(int id, String designation) {
        this.id = id;
        this.designation = designation;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "designation")
    private String designation;

}
