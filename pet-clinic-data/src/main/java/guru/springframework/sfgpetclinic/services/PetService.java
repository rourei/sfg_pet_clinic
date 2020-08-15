package guru.springframework.sfgpetclinic.services;

import guru.springframework.sfgpetclinic.model.Pet;

// The specified Type and ID type are mapped to the generics in the CrudService.
public interface PetService extends CrudService<Pet, Long> {

}
