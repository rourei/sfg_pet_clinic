package guru.springframework.sfgpetclinic.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vets")
public class Vet extends Person {

    // https://www.baeldung.com/lombok-builder-inheritance -> Lombok usually doesn't consider fields of parent classes
    // => manual implementation necessary
    @Builder
    public Vet(Long id, String firstName, String lastName, Set<Specialty> specialties) {
        super(id, firstName, lastName); // call parent constructor to set ID

        if(specialties != null) {
            this.specialties = specialties;
        }
    }

    @ManyToMany(fetch = FetchType.EAGER) // following the official implementation
    @JoinTable(name = "vet_specialties", joinColumns = @JoinColumn(name = "vet_id"),
            inverseJoinColumns = @JoinColumn(name = "specialty"))
    private Set<Specialty> specialties = new HashSet<>(); // initialize as empty set to avoid NPE on first access
}
