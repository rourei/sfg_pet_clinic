package com.rourei.sfgpetclinic.controllers;

import com.rourei.sfgpetclinic.model.Owner;
import com.rourei.sfgpetclinic.model.Pet;
import com.rourei.sfgpetclinic.model.PetType;
import com.rourei.sfgpetclinic.services.OwnerService;
import com.rourei.sfgpetclinic.services.PetService;
import com.rourei.sfgpetclinic.services.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/owners/{ownerId}")
public class PetController {

    private static final String VIEWS_PETS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdatePetForm";

    private final PetService petService;
    private final OwnerService ownerService;
    private final PetTypeService petTypeService;

    public PetController(PetService petService, OwnerService ownerService, PetTypeService petTypeService) {
        this.petService = petService;
        this.ownerService = ownerService;
        this.petTypeService = petTypeService;
    }

    @ModelAttribute("types") // binds the return value of the method to the model as attribute 'types'
    public Collection<PetType> populatePetTypes(){
        return petTypeService.findAll();
    }

    @ModelAttribute("owner") // binds the return value of the method to the model as attribute 'owner'
    public Owner findOwner(@PathVariable("ownerId") Long ownerId){
        return ownerService.findById(ownerId);
    }

    @InitBinder("owner") // allows injection of a WebDataBinder
    public void initOwnerBinder(WebDataBinder dataBinder){
        /*
         This is basically a security feature: by disallowing the ID field, the ID property cannot be changed from the
         web interface. This is important since it is the primary property for the underlying database.
        */
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/pets/new")
    public String initCreationPetForm(Owner owner, Model model) {
        // Create empty pet object and add it to the model -> empty form on creating new  pet
        Pet pet = Pet.builder().build();
        owner.getPets().add(pet);
        pet.setOwner(owner);
        model.addAttribute("pet", pet);

        return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/pets/new")
    public String processCreationForm(Owner owner, @Valid Pet pet, BindingResult result, Model model) {

        // Check if pet already exists with the current owner -> reject addition if so
        if (StringUtils.hasLength(pet.getName()) && pet.isNew() && owner.getPet(pet.getName(), true) != null){
            result.rejectValue("name", "duplicate", "already exists");
        }

        // Add new pet to the current Owner
        owner.getPets().add(pet);

        // Put content back into the form by adding the current pet state to the model and return the pre-filled form
        if (result.hasErrors()) {

            model.addAttribute("pet", pet);

            return VIEWS_PETS_CREATE_OR_UPDATE_FORM;

        } else {

            // Save the added pet and redirect to owner details
            petService.save(pet);

            return "redirect:/owners/" + owner.getId();
        }
    }

    @GetMapping("/pets/{petId}/edit")
    public String initUpdatePetForm(@PathVariable Long petId, Model model) {

        // Add the pet that should be edited to the model -> fill the view component with the current content
        model.addAttribute("pet", petService.findById(petId));

        return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/pets/{petId}/edit")
    public String processUpdateForm(@Valid  Pet pet, BindingResult result, Owner owner, Model model) {

        // Assign Owner and return the form wth the current content of the Pet object
        if (result.hasErrors()) {
            pet.setOwner(owner);
            model.addAttribute("pet", pet);
            return VIEWS_PETS_CREATE_OR_UPDATE_FORM;

        } else {

            // Add pet and save it to the database
            owner.getPets().add(pet);
            petService.save(pet);

            return "redirect:/owners/" + owner.getId();
        }
    }
}
