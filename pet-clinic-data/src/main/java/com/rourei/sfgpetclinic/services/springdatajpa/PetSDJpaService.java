package com.rourei.sfgpetclinic.services.springdatajpa;

import com.rourei.sfgpetclinic.model.Pet;
import com.rourei.sfgpetclinic.repositories.PetRepository;
import com.rourei.sfgpetclinic.services.PetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class PetSDJpaService implements PetService {

    private final PetRepository petRepository;

    // Use Spring's dependency Injection
    public PetSDJpaService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public Set<Pet> findAll() {

        // Initialize return value as empty set to avoid NPE at runtime
        Set<Pet> pets = new HashSet<>();
        // Iterate over all available pets and add each pet to the return value using a method reference
        petRepository.findAll().forEach(pets::add);

        return pets;
    }

    @Override
    public Pet findById(Long aLong) {
        // Return value from Optional if available or else return null
        return petRepository.findById(aLong).orElse(null);
    }

    @Override
    public Pet save(Pet object) {
        return petRepository.save(object);
    }

    @Override
    public void delete(Pet object) {
        petRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        petRepository.deleteById(aLong);
    }
}
