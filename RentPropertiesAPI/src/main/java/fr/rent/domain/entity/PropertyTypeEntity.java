package fr.rent.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "property_type")
public class PropertyTypeEntity {
    public PropertyTypeEntity() {

    }

    public PropertyTypeEntity(String designation) {
        this.designation = designation;
    }

    public PropertyTypeEntity(int id, String designation) {
        this.id = id;
        this.designation = designation;
    }

    @GeneratedValue
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "designation")
    private String designation;
}
