package guru.springframework.sfgpetclinic.services;

import guru.springframework.sfgpetclinic.model.Vet;

// The specified Type and ID type are mapped to the generics in the CrudService.
public interface VetService extends CrudService<Vet, Long> {

}
