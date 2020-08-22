package com.rourei.sfgpetclinic.controllers;

import com.rourei.sfgpetclinic.model.Owner;
import com.rourei.sfgpetclinic.model.PetType;
import com.rourei.sfgpetclinic.services.OwnerService;
import com.rourei.sfgpetclinic.services.PetService;
import com.rourei.sfgpetclinic.services.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
