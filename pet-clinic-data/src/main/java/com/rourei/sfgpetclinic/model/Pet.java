package com.rourei.sfgpetclinic.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pets")
public class Pet extends BaseEntity {

    // https://www.baeldung.com/lombok-builder-inheritance -> Lombok usually doesn't consider fields of parent classes
    // => manual implementation necessary
    @Builder
    public Pet(Long id, String name, PetType petType, Owner owner, LocalDate birthDate, Set<Visit> visits) {
        super(id);
        this.name = name;
        this.petType = petType;
        this.owner = owner;
        this.birthDate = birthDate;

        if (visits == null || visits.size() > 0 ) {
            this.visits = visits;
        }
    }

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "type_id") // column for foreign key that maps to the pet type ID property
    private PetType petType;

    @ManyToOne
    @JoinColumn(name = "owner_id") // column for foreign key that maps to the owner ID property
    private Owner owner;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    // mappedBy references the pet property of the Visit class
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pet")
    private Set<Visit> visits = new HashSet<>();
}
