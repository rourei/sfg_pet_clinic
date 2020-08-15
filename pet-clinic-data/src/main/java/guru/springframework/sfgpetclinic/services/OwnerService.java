package guru.springframework.sfgpetclinic.services;

import guru.springframework.sfgpetclinic.model.Owner;

// The specified Type and ID type are mapped to the generics in the CrudService.
public interface OwnerService extends CrudService<Owner, Long> {

    Owner findByLastName(String lastName);

}
