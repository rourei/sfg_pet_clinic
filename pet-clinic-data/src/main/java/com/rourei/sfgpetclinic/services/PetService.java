package com.rourei.sfgpetclinic.services;

import com.rourei.sfgpetclinic.model.Pet;

// The specified Type and ID type are mapped to the generics in the CrudService.
public interface PetService extends CrudService<Pet, Long> {

}
