package com.rourei.sfgpetclinic.services.springdatajpa;

import com.rourei.sfgpetclinic.model.Owner;
import com.rourei.sfgpetclinic.repositories.OwnerRepository;
import com.rourei.sfgpetclinic.repositories.PetRepository;
import com.rourei.sfgpetclinic.repositories.PetTypeRepository;
import com.rourei.sfgpetclinic.services.OwnerService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

// Provide different implementation of the corresponding interface
@Service // mark as Spring managed component
@Profile("springdatajpa") // use this implementation only in the specified profile
public class OwnerSDJpaService implements OwnerService {

    private final OwnerRepository ownerRepository;
    private final PetRepository petRepository; // Might be necessary later on
    private final PetTypeRepository petTypeRepository; // Might be necessary later on

    // Uses Springs Dependency Injection
    public OwnerSDJpaService(OwnerRepository ownerRepository, PetRepository petRepository,
                             PetTypeRepository petTypeRepository) {
        this.ownerRepository = ownerRepository;
        this.petRepository = petRepository;
        this.petTypeRepository = petTypeRepository;
    }

    @Override
    public Owner findByLastName(String lastName) {
        return ownerRepository.findByLastName(lastName);
    }

    @Override
    public Set<Owner> findAll() {
        ownerRepository.findAll();

        // Initialize return value as empty set to avoid NPE at runtime
        Set<Owner> owners = new HashSet<>();

        // Iterate over all available owners and add each owner to the return value
        ownerRepository.findAll().forEach(owners::add); // using a method reference

        return owners;
    }

    @Override
    public Owner findById(Long aLong) {

        // Method provided by CrudRepository returns an Optional
        Optional<Owner> optionalOwner = ownerRepository.findById(aLong);

        // Return owner object if available or null else
        if (optionalOwner.isPresent()){
            return optionalOwner.get(); // access value via Optional.get()
        } else {
            return null;
        }

        // !!! Using the functionality of the Optionals, the entire method can be compressed to
        // return ownerRepository.findById(aLong).orElse(null);
    }

    @Override
    public Owner save(Owner object) {
        return ownerRepository.save(object);
    }

    @Override
    public void delete(Owner object) {

        ownerRepository.delete(object);

    }

    @Override
    public void deleteById(Long aLong) {

        ownerRepository.deleteById(aLong);

    }
}
