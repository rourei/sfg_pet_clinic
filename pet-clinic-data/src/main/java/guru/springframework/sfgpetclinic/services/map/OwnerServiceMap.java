package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import org.springframework.stereotype.Service;

import java.util.Set;

// Implements the OwnerService interface by using the methods of the abstract parent class.

@Service
public class OwnerServiceMap extends AbstractMapService<Owner, Long> implements OwnerService {

    private final PetTypeService petTypeService;
    private final PetService petService;

    // Using Spring's Dependency Injection
    public OwnerServiceMap(PetTypeService petTypeService, PetService petService) {
        this.petTypeService = petTypeService;
        this.petService = petService;
    }

    @Override
    public Set<Owner> findAll() {
        return super.findAll(); // calling the method of the parent class
    }

    @Override
    public Owner findById(Long id) {
        return super.findById(id); // calling the method of the parent class
    }

    @Override
    public Owner save(Owner object) {

        // This save-method also checks if all properties of the assigned pets are set.
        // If not, it uses the injected services to assign the missing properties from within the database.

        if (object != null) {
            // Check if owner has any pets
            if (object.getPets() != null) {
                // Iterate over all existing pets
                object.getPets().forEach(pet -> {
                    // Check if PetType is specified , if not -> runtime exception
                    if (pet.getPetType() != null){
                        // Check if ID property is already set -> ensure consistency with already saved pet types
                        if (pet.getPetType().getId() == null) {
                            // Use injected petTypeService to assign ID if not specified yet
                            pet.setPetType(petTypeService.save(pet.getPetType()));
                        }
                    } else {
                        throw new RuntimeException("Pet Type is required.");
                    }

                    // Check if pet ID is already specified
                    if (pet.getId() == null) {
                        // If not, use injected petService to assign it
                        Pet savedPet = petService.save(pet);
                        // Use the saved pet (carrying an ID) to set the pet ID -> consistency
                        pet.setId(savedPet.getId());
                    }
                });
            }
            // Call method of the parent class
            return super.save(object);

        } else {
            return null;
        }
    }

    @Override
    public void delete(Owner object) {
        super.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public Owner findByLastName(String lastName) {
        return null;
    }
}
