package guru.springframework.sfgpetclinic.model;

import java.io.Serializable;

public class BaseEntity implements Serializable {

    private Long id; // Hibernate recommends box types over primitives (Long instead of long)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
