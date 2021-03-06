package com.rourei.sfgpetclinic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class Person extends BaseEntity {

    // Manual definition to allow usage of inheritance inside of Lombok
    public Person(Long id, String firstName, String lastName) {
        super(id); // call parent constructor to set ID
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Column(name = "first_name") // define column name. Redundant since snake case is Hibernate standard, only for clarity
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
}
