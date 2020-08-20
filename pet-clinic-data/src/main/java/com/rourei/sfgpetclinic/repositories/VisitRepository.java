package com.rourei.sfgpetclinic.repositories;

import com.rourei.sfgpetclinic.model.Visit;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

// Arguments for CRUDRepository are object type (Visit) and ID value type (Long)
public interface VisitRepository extends CrudRepository<Visit, Long> {

    // The implementation of these repositories is provided by Spring at runtime to provide persistence and
    // query operations.
    // Under the hood, this repository takes care of Hibernate related commands, transactions, etc.

    // The original findAll() method from the CrudRepository returns an Iterable. Since the Map-based implementation
    // returns a Set of Visits we need to 'convert' that return value into a set.
    Set<Visit> findAll();

}
