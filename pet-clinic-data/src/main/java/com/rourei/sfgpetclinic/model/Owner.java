package com.rourei.sfgpetclinic.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "owners")
public class Owner extends Person {

    // https://www.baeldung.com/lombok-builder-inheritance -> Lombok usually doesn't consider fields of parent classes
    // => manual implementation necessary
    @Builder
    public Owner(Long id, String firstName, String lastName, String address, String city,
                 String telephone, Set<Pet> pets) {
        super(id, firstName, lastName); // call parent constructor to set ID
        this.address = address;
        this.city = city;
        this.telephone = telephone;

        if(pets != null) {
            this.pets = pets;
        }
    }

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "telephone")
    private String telephone;

    // Cascade: if Owner is deleted, all his pets are deleted too
    // mappedBy turns the relationship into a bidirectional one
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private Set<Pet> pets = new HashSet<>(); // initialize as empty set to avoid NPE on first access

     // Return the Pet with the given name, or null if none found for this Owner.
    public Pet getPet(String name) {
        return getPet(name, false);
    }

    // Return the Pet with the given name, or null if none found for this Owner.
    public Pet getPet(String name, boolean ignoreNew) {

        name = name.toLowerCase();

        for (Pet pet : pets) {
            if (!ignoreNew || !pet.isNew()) {
                String compName = pet.getName();
                compName = compName.toLowerCase();
                if (compName.equals(name)) {
                    return pet; // Return current pet if names are equal
                }
            }
        }
        return null;
    }
}
