package guru.springframework.sfgpetclinic.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

// @Test marks the individual unit tests

class OwnerTest {

    Owner owner;

    @BeforeEach // runs before each individual test
    void setUp() {
        // (Re-)Initialize Owner -> new Owner instance for every test
        owner = new Owner();
    }

    @Test
    void getId() {

        // Define and assign value
        Long idValue = 42L;
        owner.setId(idValue);

        // Test return value
        assertEquals(idValue, owner.getId());
    }

    @Test
    void getAddress() {

        // Define and assign value
        String address = new String("123 Street");
        owner.setAddress(address);

        // Test return value
        assertEquals(address, owner.getAddress());
    }

    @Test
    void getPets() {

        // Define and assign value
        Set<Pet> pets = new HashSet<>();
        owner.setPets(pets);

        // test return value
        assertEquals(pets, owner.getPets());
    }
}