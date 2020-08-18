package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Specialty;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.SpecialtyService;
import guru.springframework.sfgpetclinic.services.VetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

// Implements the VetService interface by using the methods of the abstract parent class.

@Service
@Profile({"default", "map"}) // as soon as an active profile is specified in the config, default will not be loaded -> add map
public class VetMapService extends AbstractMapService<Vet, Long> implements VetService {

    private final SpecialtyService specialtyService;

    public VetMapService(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    @Override
    public Set<Vet> findAll() {
        return super.findAll();
    }

    @Override
    public Vet findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Vet save(Vet object) {

        // If a vet has a specialty that has not been persisted, do that first
        if (object.getSpecialties().size() > 0) {
            // Iterate over all specialties
            object.getSpecialties().forEach(specialty ->{
                // Check if ID has been set already
                if (specialty.getId() == null){
                    // If not, assign via injected service
                    Specialty savedSpecialty = specialtyService.save(specialty);
                    // Assign generated ID to current object for consistency
                    specialty.setId(savedSpecialty.getId());
                }
            });
        }

        return super.save(object);
    }

    @Override
    public void delete(Vet object) {
        super.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }
}
