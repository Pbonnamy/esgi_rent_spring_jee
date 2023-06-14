package fr.rent.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "property_type")
public class PropertyTypeEntity {

    public PropertyTypeEntity(String designation) {
        this.designation = designation;
    }

    public PropertyTypeEntity(int id, String designation) {
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
