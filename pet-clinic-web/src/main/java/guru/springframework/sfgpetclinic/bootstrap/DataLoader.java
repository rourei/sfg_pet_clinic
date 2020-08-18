package guru.springframework.sfgpetclinic.bootstrap;

import guru.springframework.sfgpetclinic.model.*;
import guru.springframework.sfgpetclinic.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component // mark as Spring Bean -> make Spring call the run method
public class DataLoader implements CommandLineRunner {

    // Properties
    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialtyService specialtyService;
    private final VisitService visitService;

    // now using Spring's Dependency Injection, no @Autowired necessary for constructor injection
    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService,
                      SpecialtyService specialtyService, VisitService visitService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialtyService = specialtyService;
        this.visitService = visitService;
    }

    @Override
    public void run(String... args) throws Exception {

        // Only load data if no data exists -> future proof for using the class later on with MySQL, etc.
        int count = petTypeService.findAll().size();

        if (count == 0){
            loadData();
        }

    }

    // Data initialization method
    private void loadData() {
        // ###### CREATE PET TYPES ######
        PetType dog = new PetType();
        dog.setName("Dog");
        PetType savedDogPetType = petTypeService.save(dog); // enables us to use the assigned ID later on

        PetType cat = new PetType();
        cat.setName("Cat");
        PetType savedCatPetType = petTypeService.save(cat); // enables us to use the assigned ID later on

        System.out.println("Loaded pet types...");

        // ###### CREATE SPECIALTIES ######
        Specialty radiology = new Specialty();
        radiology.setDescription("Radiology");
        Specialty savedRadiology = specialtyService.save(radiology);

        Specialty dentistry = new Specialty();
        dentistry.setDescription("Dentistry");
        Specialty savedDentistry = specialtyService.save(dentistry);

        Specialty surgery = new Specialty();
        surgery.setDescription("Surgery");
        Specialty savedSurgery = specialtyService.save(surgery);

        System.out.println("Loaded specialties...");

        // ###### CREATE OWNERS AND PETS ######
        Owner owner1 = new Owner();
        owner1.setFirstName("Mike");
        owner1.setLastName("Tyson");
        owner1.setAddress("123 Street");
        owner1.setCity("Miami");
        owner1.setTelephone("123-456-789");

        Pet mikesPet = new Pet();
        mikesPet.setPetType(savedDogPetType); // Saved type already has an ID assigned (see above)
        mikesPet.setName("Rosco");
        mikesPet.setOwner(owner1); // Assign owner
        mikesPet.setBirthDate(LocalDate.now());
        owner1.getPets().add(mikesPet); // Add pet to set (method chaining -> immediately modify return value)

        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Hannah");
        owner2.setLastName("Fry");
        owner2.setAddress("546 Street");
        owner2.setCity("Washington");
        owner2.setTelephone("987-654-321");

        Pet hannahsPet = new Pet();
        hannahsPet.setPetType(savedCatPetType); // Saved type already has an ID assigned (see above)
        hannahsPet.setName("Minka");
        hannahsPet.setOwner(owner2); // Assign owner
        hannahsPet.setBirthDate(LocalDate.now());
        owner2.getPets().add(hannahsPet); // Add pet to set (method chaining -> immediately modify return value)

        // Creating the visit --here-- throws an RTE since the owner (Hannah) has not been persisted (save below)
        ownerService.save(owner2);

        // ###### CREATE NEW VISIT ENTITY ######
        Visit catVisit = new Visit();
        catVisit.setPet(hannahsPet);
        catVisit.setDate(LocalDate.now());
        catVisit.setDescription("General CheckUp.");

        // Saving the visit after saving the owner is safe since the owner and the pet now have an ID assigned
        visitService.save(catVisit);

        System.out.println("Loaded owners and visits...");

        // ###### CREATE VETS ######
        Vet vet1 = new Vet();
        vet1.setFirstName("Michael");
        vet1.setLastName("Jackson");
        vet1.getSpecialties().add(savedRadiology); // Add specialty to set (method chaining)

        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Gloria");
        vet2.setLastName("Gaynor");
        vet2.getSpecialties().add(savedSurgery); // Add specialty to set (method chaining)

        vetService.save(vet2);

        System.out.println("Loaded vets...");
    }
}
