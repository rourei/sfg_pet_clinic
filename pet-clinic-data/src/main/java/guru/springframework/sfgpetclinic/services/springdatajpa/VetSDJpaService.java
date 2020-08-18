package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.repositories.VetRepository;
import guru.springframework.sfgpetclinic.services.VetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

// Provide different implementation of the corresponding interface
@Service // mark as Spring managed component
@Profile("springdatajpa") // use this implementation only in the specified profile
public class VetSDJpaService implements VetService {

    private final VetRepository vetRepository;

    // Uses Springs Dependency Injection
    public VetSDJpaService(VetRepository vetRepository) {
        this.vetRepository = vetRepository;
    }

    @Override
    public Set<Vet> findAll() {

        // Initialize return value as empty set to avoid NPE at runtime
        Set<Vet> vets = new HashSet<>();

        // Iterate over all available vets and add each vet to the return value using a method reference
        vetRepository.findAll().forEach(vets::add);

        return vets;
    }

    @Override
    public Vet findById(Long aLong) {
        // Return value from Optional if available or else return null
        return vetRepository.findById(aLong).orElse(null);
    }

    @Override
    public Vet save(Vet object) {
        return vetRepository.save(object);
    }

    @Override
    public void delete(Vet object) {
        vetRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        vetRepository.deleteById(aLong);
    }
}
