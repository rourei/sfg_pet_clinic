package com.rourei.sfgpetclinic.services.map;

import com.rourei.sfgpetclinic.model.Visit;
import com.rourei.sfgpetclinic.services.VisitService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

// Implements the VisitService interface by using the methods of the abstract parent class.

@Service
@Profile({"default", "map"}) // as soon as an active profile is specified in the config, default will not be loaded -> add map
public class VisitMapService extends AbstractMapService<Visit, Long> implements VisitService {

    @Override
    public Set<Visit> findAll() {
        return super.findAll();
    }

    @Override
    public Visit findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Visit save(Visit visit) {

        // If either the corresponding pet or owner (and their respective IDs) are not set (i. e. not yet persisted),
        // throw a RTE)
        if (visit.getPet() == null || visit.getPet().getId() == null
        || visit.getPet().getOwner() == null || visit.getPet().getOwner().getId() == null){
            throw new RuntimeException("Invalid Visit.");
        }

        // No RTE -> persist the visit via the parent method
        return super.save(visit);
    }

    @Override
    public void delete(Visit object) {
        super.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }
}
