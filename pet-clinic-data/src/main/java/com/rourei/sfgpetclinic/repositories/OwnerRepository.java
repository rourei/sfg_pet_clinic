package com.rourei.sfgpetclinic.repositories;

import com.rourei.sfgpetclinic.model.Owner;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

// Arguments for CRUDRepository are object type (Owner) and ID value type (Long)
public interface OwnerRepository extends CrudRepository<Owner, Long> {

    // The implementation of these repositories is provided by Spring at runtime to provide persistence and
    // query operations.
    // Under the hood, this repository takes care of Hibernate related commands, transactions, etc.

    Owner findByLastName(String lastName);

    // The original findAll() method from the CrudRepository returns an Iterable. Since the Map-based implementation
    // returns a Set of Owners we need to 'convert' that return value into a set.
    Set<Owner> findAll();

    List<Owner> findAllByLastNameLike(String lastName); // LIKE stems from SQL (often used in WHERE clause)

}
