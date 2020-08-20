package com.rourei.sfgpetclinic.services.springdatajpa;

import com.rourei.sfgpetclinic.model.Specialty;
import com.rourei.sfgpetclinic.repositories.SpecialtyRepository;
import com.rourei.sfgpetclinic.services.SpecialtyService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class SpecialtySDJpaService implements SpecialtyService {

    private final SpecialtyRepository specialtyRepository;

    // Use Spring's Dependency Injection
    public SpecialtySDJpaService(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    @Override
    public Set<Specialty> findAll() {

        // Initialize return value as empty set to avoid NPE at runtime
        Set<Specialty> specialties = new HashSet<>();

        // Iterate over all available specialties and add each specialty to the return value using a method reference
        specialtyRepository.findAll().forEach(specialties::add);

        return specialties;
    }

    @Override
    public Specialty findById(Long aLong) {
        // Return value from Optional if available or else return null
        return specialtyRepository.findById(aLong).orElse(null);
    }

    @Override
    public Specialty save(Specialty object) {
        return specialtyRepository.save(object);
    }

    @Override
    public void delete(Specialty object) {
        specialtyRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        specialtyRepository.deleteById(aLong);
    }
}
