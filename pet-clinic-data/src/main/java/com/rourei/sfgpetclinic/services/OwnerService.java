package com.rourei.sfgpetclinic.services;

import com.rourei.sfgpetclinic.model.Owner;

import java.util.List;

// The specified Type and ID type are mapped to the generics in the CrudService.
public interface OwnerService extends CrudService<Owner, Long> {

    Owner findByLastName(String lastName);

    List<Owner> findAllByLastNameLike(String lastName);

}
