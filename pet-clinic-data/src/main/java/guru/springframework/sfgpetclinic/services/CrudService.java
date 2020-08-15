package guru.springframework.sfgpetclinic.services;

import java.util.Set;

// Service to mimic the Spring Data JPA CRUD Repository using Java generics.
// Within the extends statement, the Type and ID type are specified that are mapped to those generics.

public interface CrudService<T, ID> {

    Set<T> findAll();

    T findById(ID id);

    T save(T object);

    void delete(T object);

    void deleteById(ID id);
}
