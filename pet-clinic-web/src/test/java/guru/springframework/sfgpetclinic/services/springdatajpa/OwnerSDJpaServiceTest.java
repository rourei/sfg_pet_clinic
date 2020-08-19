package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // Set up the JUnit5 environment for Mockito -> init Mocks automatically
class OwnerSDJpaServiceTest {

    @Mock
    OwnerRepository ownerRepository; // Under the hood, the OwnerSDJpaService calls the repository methods

    // Class under test
    @InjectMocks // makes @BeforeEach method obsolete (still implemented for re-initialization of the returnOwner)
    OwnerSDJpaService service;

    // Centralized definitions of properties
    final String LAST_NAME = "Smith";
    final Long OWNER_ID = 42L;

    // Create new Owner instance to be used for the tests
    Owner returnOwner ;

    @BeforeEach
    void setUp() {
        // Re-Initialize the object before each test (just in case something breaks/modifies the object)
        returnOwner = Owner.builder().id(OWNER_ID).lastName(LAST_NAME).build();
    }

    @Test
    void findByLastName() {

        // WHEN the findByLastName method is called with ANY parameters, THEN RETURN the Owner object instantiated above
        when(ownerRepository.findByLastName(any())).thenReturn(returnOwner);

        // Call the method under test
        Owner smith = service.findByLastName(LAST_NAME);

        // Ensure that the method returned the correct Owner object
        assertEquals(LAST_NAME, smith.getLastName());
        // Ensure that the findByLastName method of the Mock repository has been called
        verify(ownerRepository).findByLastName(any());
    }

    @Test
    void findAll() {

        // Initialize return value for the Mock repository and add Owners
        Set<Owner> returnOwnerSet = new HashSet<>();
        returnOwnerSet.add(Owner.builder().id(1L).build());
        returnOwnerSet.add(Owner.builder().id(2L).build());

        // Define the Mock behaviour
        when(ownerRepository.findAll()).thenReturn(returnOwnerSet);

        // Call the method under test
        Set<Owner> owners = service.findAll();

        // Ensure that the return value is not null
        assertNotNull(owners);
        // Ensure that the return value contains the correct amount of Owners
        assertEquals(2, owners.size());
    }

    @Test
    void findById() {

        // Define the Mock behavior - !Attention: findById returns an Optional type (see implementation)!
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(returnOwner));

        // Call the method under test
        Owner owner = service.findById(OWNER_ID);

        // Make sure the returned Owner exists and has the correct ID assigned
        assertNotNull(owner);
        assertEquals(OWNER_ID, owner.getId());
    }

    @Test
    void findByIdNotFound() {

        // Define the Mock behavior - !Attention: findById returns an Optional type (see implementation)!
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Call the method under test
        Owner owner = service.findById(OWNER_ID);

        // Make sure the method returns null if nothing no corresponding Owner is found
        assertNull(owner);
    }

    @Test
    void save() {

        // Create new Owner instance to save
        Owner ownerToSave = Owner.builder().id(1L).build();

        // Define Mock behaviour
        when(ownerRepository.save(any())).thenReturn(returnOwner);

        // call method to test
        Owner savedOwner = service.save(ownerToSave);

        // Check that return value is not null and the correct mock method is called
        assertNotNull(savedOwner);
        verify(ownerRepository).save(any());

    }

    // The implementation of the following methods returns void -> use the verify method (default 1 call expected)

    @Test
    void delete() {

        // Call the method under test
        service.delete(returnOwner);

        // Ensure that the corresponding repository method is called
        verify(ownerRepository).delete(any());
    }

    @Test
    void deleteById() {

        // Call the method under test
        service.deleteById(OWNER_ID);

        // Ensure that the correct repository method is called
        verify(ownerRepository).deleteById(anyLong());
    }
}