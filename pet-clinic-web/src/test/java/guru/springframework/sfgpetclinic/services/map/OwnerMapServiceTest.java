package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerMapServiceTest {

    /*
     No Mock objects of the PetService and PetTypeService necessary since the 'real' implementation can be used.
     The reason for that is that it is a simple Map-based implementation that does not rely on the Spring Context to be
     injected into this class.
    */

    // The class under test
    OwnerMapService ownerMapService;

    // Centralized definition os used properties
    final Long ownerId = 1L;
    final String lastName = "Smith";

    @BeforeEach
    void setUp() {
        // Basically mimics the Dependency Injection. Possible since we coded to the interface.
        ownerMapService = new OwnerMapService(new PetTypeMapService(), new PetMapService());

        // Insert a new Owner using the Builder Pattern implemented by Lombok
        ownerMapService.save(Owner.builder().id(ownerId).lastName(lastName).build());
    }

    @Test
    void findAll() {

        // Call method under test and store result
        Set<Owner> ownerSet = ownerMapService.findAll();

        // Ensure the size of the returned Set is correct
        assertEquals(1, ownerSet.size());
    }

    @Test
    void findById() {

        // Call method under test and store result
        Owner owner = ownerMapService.findById(ownerId);

        // Ensure the returned ID is correct
        assertEquals(1L, owner.getId());

    }

    @Test // Save an Owner with an existing ID and ensure that the method returns the correct Owner as intended
    void saveExistingId() {

        // Setting up the Owner to be saved
        Long id = 2L;
        Owner owner2 = Owner.builder().id(id).build();
        // Save the Owner and store the result
        Owner savedOwner2 = ownerMapService.save(owner2);
        // Make sure the save() method returned the correct Owner
        assertEquals(id, savedOwner2.getId());
    }

    @Test // save an Owner without an ID and ensure that the ID is set by the implementation
    void saveNoId() {

        // Create and save new Owner without and ID assigned
        Owner savedOwner = ownerMapService.save(Owner.builder().build());

        // Ensure the Owner is actually saved
        assertNotNull(savedOwner);
        // Ensure the Owner has an ID assigned after being saved
        assertNotNull(savedOwner.getId());

    }

    @Test
    void deleteById() {

        // Delete the stored Owner by ID
        ownerMapService.deleteById(ownerId);

        // Ensure the Owner has been removed from the Set
        assertEquals(0, ownerMapService.findAll().size());
    }

    @Test
    void delete() {

        // Delete the sored Owner by Object
        ownerMapService.delete(ownerMapService.findById(ownerId));

        // Ensure the Owner has been removed from the Set
        assertEquals(0, ownerMapService.findAll().size());
    }

    @Test
    void findByLastName() {

        // Search for Owner and save the returned object
        Owner smith = ownerMapService.findByLastName(lastName);

        // Make sure the Owner actually exists
        assertNotNull(smith);

        // Make sure the returned object is the correct one by comparing ID values
        assertEquals(ownerId, smith.getId());
    }

    @Test
    void findByLastNameNotFound() {

        // Search for Owner that does not exist
        Owner nonExistingOwner = ownerMapService.findByLastName("FOO");

        // Make sure the method returns 'null' as intended
        assertNull(nonExistingOwner);
    }
}