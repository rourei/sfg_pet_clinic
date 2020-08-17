package guru.springframework.sfgpetclinic.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass // tells JPA that this is a base class that others inherit from. Does not need to be mapped to the database
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // retrieve ID from database, ties to specific database (MySQL here)
    private Long id; // Hibernate recommends box types over primitives (Long instead of long)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
