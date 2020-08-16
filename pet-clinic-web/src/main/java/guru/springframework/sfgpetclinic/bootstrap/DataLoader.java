package guru.springframework.sfgpetclinic.bootstrap;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component // mark as Spring Bean -> make Spring call the run method
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;

    // now using Dependency Injection, no @Autowired necessary for constructor injection
    public DataLoader(OwnerService ownerService, VetService vetService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
    }

    @Override
    public void run(String... args) throws Exception {

        Owner owner1 = new Owner();
        owner1.setFirstName("Mike");
        owner1.setLastName("Tyson");

        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Hannah");
        owner2.setLastName("Fry");

        ownerService.save(owner2);

        System.out.println("Loaded owners...");

        Vet vet1 = new Vet();
        vet1.setFirstName("Michael");
        vet1.setLastName("Jackson");

        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Gloria");
        vet2.setLastName("Gaynor");

        vetService.save(vet2);

        System.out.println("Loaded vets...");


    }
}
