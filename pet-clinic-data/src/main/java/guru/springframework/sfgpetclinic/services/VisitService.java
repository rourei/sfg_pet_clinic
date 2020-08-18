package guru.springframework.sfgpetclinic.services;

import guru.springframework.sfgpetclinic.model.Visit;

// The specified Type and ID type are mapped to the generics in the CrudService.
public interface VisitService extends CrudService<Visit, Long> {
}
