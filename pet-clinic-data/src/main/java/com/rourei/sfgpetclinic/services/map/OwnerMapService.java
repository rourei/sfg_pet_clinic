package com.rourei.sfgpetclinic.services.map;

import com.rourei.sfgpetclinic.model.Owner;
import com.rourei.sfgpetclinic.model.Pet;
import com.rourei.sfgpetclinic.services.OwnerService;
import com.rourei.sfgpetclinic.services.PetService;
import com.rourei.sfgpetclinic.services.PetTypeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

// Implements the OwnerService interface by using the methods of the abstract parent class.

@Service
@Profile({"default", "map"}) // as soon as an active profile is specified in the config, default will not be loaded -> add map
public class OwnerMapService extends AbstractMapService<Owner, Long> implements OwnerService {

    private final PetTypeService petTypeService;
    private final PetService petService;

    // Using Spring's Dependency Injection
    public OwnerMapService(PetTypeService petTypeService, PetService petService) {
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
        return this.findAll()
                .stream()
                .filter(owner -> owner.getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .orElse(null);
        /*
         Returns all existing Owners, 'streams' (i.e. iterates) over them and filters the ones out that have a
         matching last name. From this filtered stream, the first one is returned, which is an Optional.
         From the Optional either the found Owner is returned or null if no matching Owner exists.
        */
    }

    @Override
    public List<Owner> findAllByLastNameLike(String lastName) {

        // todo - implementation
        return null;
    }
}
